package com.mage.binasehat.data.local.db.mapper

import com.mage.binasehat.R
import com.mage.binasehat.data.model.BodyPartSegmentValue
import com.mage.binasehat.data.model.Category
import com.mage.binasehat.data.model.Exercise
import com.mage.binasehat.data.model.InteractiveExerciseSetting
import com.mage.binasehat.data.remote.response.DetailExerciseRespone
import com.mage.binasehat.data.remote.response.InteractiveBodyPartSegmentValue
import com.mage.binasehat.data.remote.response.InteractiveSetting

object ExerciseMapper {
    // Create a default category for when the response category is null
    private val DEFAULT_CATEGORY = Category("Unknown")

    fun mapDetailResponseToExercise(response: DetailExerciseRespone): Exercise {
        val data = response.data ?: throw IllegalStateException("Exercise data is null")

        // Convert the ID to Long explicitly
        val id = data.id?.toLong() ?: throw IllegalStateException("Exercise ID is null")

        return Exercise(
            id = id,  // Now guaranteed to be Long
            name = data.name ?: "",
            rating = data.rating ?: 0,
            level = data.level ?: 0,
            calEstimation = data.calEstimation ?: 0,
            requiredEquiment = data.requiredEquipment == 1,
            explain = data.overview ?: "",
            step = data.step?.split("\n")?.toTypedArray() ?: arrayOf(""),
            category = data.category ?: DEFAULT_CATEGORY,
            isSupportInteractive = data.isSupportInteractive == 1,
            interactiveSetting = mapInteractiveSetting(data.interactiveSetting),
            interctiveBodyPartSegmentValue = mapBodyPartSegmentValue(data.interactiveBodyPartSegmentValue),
            bodyPartNeeded = data.bodyPartNeeded?.filterNotNull()?.toTypedArray() ?: arrayOf(""),
            muscle = data.muscle ?: throw IllegalStateException("Muscle data is null"),
            photo = data.photoUrl ?: 0,
            Gif = data.gifUrl ?: 0
        )
    }

    private fun mapInteractiveSetting(remote: InteractiveSetting?): InteractiveExerciseSetting {
        return InteractiveExerciseSetting(
            repetion = remote?.repetition ?: 0,
            set = remote?.set ?: 0,
            RestInterval = (remote?.restInterval ?: 0).toLong()
        )
    }

    private fun mapBodyPartSegmentValue(remote: InteractiveBodyPartSegmentValue?): BodyPartSegmentValue {
        return BodyPartSegmentValue(
            rightArm = remote?.rightArm?.toDouble() ?: 0.0,
            leftArm = remote?.leftArm?.toDouble() ?: 0.0,
            rightLeg = remote?.rightLeg?.toDouble() ?: 0.0,
            leftLeg = remote?.leftLeg?.toDouble() ?: 0.0
        )
    }

    private fun getDrawableResourceForUrl(url: String?): Int {
        // Here you would implement the logic to map URLs to your drawable resources
        // For now, returning a default drawable resource
        return R.drawable.placeholder_image  // Replace with your actual default drawable
    }
}