package com.mage.binasehat.data.local.fake

import androidx.compose.ui.res.painterResource
import com.mage.binasehat.R
import com.mage.binasehat.data.model.BodyPartSegmentValue
import com.mage.binasehat.data.model.Category
import com.mage.binasehat.data.model.Exercise
import com.mage.binasehat.data.model.Food
import com.mage.binasehat.data.model.InteractiveExerciseSetting
import com.mage.binasehat.data.model.Muscle

object FakeData {

    private val fakeMuscleData = listOf(
        Muscle(id = 1, "Chest"),
        Muscle(id = 2, "Full Body"),
        Muscle(id = 3, "Legs"),
        Muscle(id = 4, "Arms"),
        Muscle(id = 5, "Back"),

    )

    private val fakeCategory = listOf(
        Category("Strength"),
        Category("Cardio"),

    )

    val fakeExerciseData = listOf(
        Exercise(
            id = 1,
            name = "Dumbbell Curl",
            rating = 99,
            level = 1,
            calEstimation = 150,
            requiredEquiment = true,
            explain = "The dumbbell curl is a bicep exercise that targets the muscles in your arms.",
            step = arrayOf(
                "Stand with your feet shoulder-width apart, holding a dumbbell in each hand with your arms fully extended and palms facing forward.",
                "Keep your upper arms stationary and exhale as you curl the weights while contracting your biceps.",
                "Inhale and slowly begin to lower the dumbbells back to the starting position."
            ),
            isSupportInteractive = true,
            interactiveSetting = InteractiveExerciseSetting(
                repetion = 12,
                set = 3,
                RestInterval = 60
            ),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 10.0,
                leftArm = 10.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            bodyPartNeeded = arrayOf("right_hand" , "left_hand" ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[3],
            photo = R.drawable.dumbbel_bicep_cover,
            Gif = R.drawable.dumbbel_bicep
        ),
        Exercise(
            id = 2,

            name = "Push-ups",
            rating = 97,
            level = 2,
            calEstimation = 55,
            requiredEquiment = false,
            interactiveSetting = InteractiveExerciseSetting(
                repetion = 6,
                set = 3,
                RestInterval = 60

            ),
            isSupportInteractive = true,
            bodyPartNeeded = arrayOf("right_hand" , "left_hand" ),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 55.0,
                leftArm = 49.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = "Bodyweight exercise targeting the chest, shoulders, and triceps.",
            step = arrayOf(
                "Start in a plank position.",
                "Lower your body until your chest nearly touches the floor.",
                " Push back up to the starting position."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[3],
            photo = R.drawable.push_up,
            Gif = R.drawable.push_up_model
        ),
        Exercise(
            id = 3,
            name = "Dumbbell Sumo Squat ",
            level = 3,
            rating = 97,
            calEstimation = 150,
            requiredEquiment = false,
            interactiveSetting = InteractiveExerciseSetting(
                repetion = 10,
                set = 3,
                RestInterval = 60

            ),
            isSupportInteractive = true,
            bodyPartNeeded = arrayOf("right_leg" , "left_leg" ),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 110.6,
                leftLeg= 110.7,
            ),
            explain = "A variation of the traditional squat that targets the inner thighs and glutes.",
            step = arrayOf(
                "Stand with feet wider than shoulder-width, toes pointing out.",
                "Hold a dumbbell with both hands in front of you.",
                "Squat down, keeping your back straight.",
                "Return to the starting position."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.sumo_squat_cover,
            Gif = R.drawable.sumo_squat_model
        ),
        Exercise(
            id = 4,
            name = "Dumbbell Biceps Curl on Bosu Ball",
            rating = 96,
            level = 1,
            calEstimation = 180,
            requiredEquiment = true,
            isSupportInteractive = true,
            interactiveSetting = InteractiveExerciseSetting(
                repetion = 14,
                set = 3,
                RestInterval = 60
            ),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 10.0,
                leftArm = 10.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            bodyPartNeeded = arrayOf("right_hand" , "left_hand" ),
            explain = "An exercise that combines core stabilization with bicep strengthening.",
            step = arrayOf(
                "Sit on a Bosu ball in a V-sit position.",
                "Perform bicep curls while maintaining balance.",
                "Repeat"
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[3],
            photo = R.drawable.dumbbel_bicep_cover,
            Gif = R.drawable.biceps_curl_ball_model
        ),


        Exercise(
            id = 5,
            name = "Dumbbell Goblet Wall Sit",
            rating = 95,
            level = 3,
            calEstimation = 40,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = "A lower body exercise that improves endurance and strength in the legs.",
            step = arrayOf(
                "Stand with your back against a wall.",
                "Hold a dumbbell at chest level.",
                "Slide down the wall into a sitting position.",
                "Hold the position."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 6,
            name = "Assault Bike Run",
            rating = 88,
            level = 1,
            calEstimation = 180,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = "An intense cardio workout on the assault bike, engaging both upper and lower body.",
            step = arrayOf(
                "Sit on the assault bike.",
                "Grab the handles.",
                "Start pedaling and pushing/pulling the handles.",
                "Hold the position."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 7,
            name = " Bench Press Conventional Grip",
            rating = 85,
            level = 3,
            calEstimation = 50,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = " bench press variation that starts from a dead stop, targeting the chest, shoulders, and triceps.",
            step = arrayOf(
                "Lie on a bench with the barbell resting on safety pins at chest level.",
                "Grip the barbell with a conventional grip.",
                "Press the barbell up until arms are fully extended.",
                "Lower the barbell back to the pins."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 8,
            name = "  Dumbell Bulgarian Split Squat ",
            rating = 82,
            level = 3,
            calEstimation = 50,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = " A challenging variation of the Bulgarian split squat that increases range of motion and targets the lower body.",
            step = arrayOf(
                "Place one foot on an elevated surface behind you.",
                "Hold a dumbbell in each hand.",
                "Squat down on the front leg, keeping your torso upright.",
                "Push back up to the starting position."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 9,
            name = "Dumbbell Incline Triceps Kickback",
            rating = 82,
            level = 3,
            calEstimation = 14,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = "Targets the triceps muscles by extending the arms on an incline bench.",
            step = arrayOf(
                "Lean forward on an incline bench.",
                "Hold a dumbbell in each hand.",
                "Extend your arms backward, focusing on the triceps.",

                ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 10,
            name = "Smith Sumo Chair Squat",
            rating = 77,
            level = 3,
            calEstimation = 50,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = "A squat variation using the Smith machine for stability, targeting the thighs and glutes.",
            step = arrayOf(
                "Stand with feet wider than shoulder-width, toes out.",
                "Position yourself under the Smith machine bar.",
                "Squat down as if sitting in a chair.",

                ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 11,
            name = "Walking on Elliptical Machine",
            rating = 88,
            level = 2,
            calEstimation = 100,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = " A low-impact cardio exercise on the elliptical machine, ideal for overall fitness and endurance.",
            step = arrayOf(
                "Stand on the elliptical machine.",
                "Hold onto the handles.",
                "Start walking, adjusting the resistance as needed.",
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
    )

    val fakeFoodData = listOf(
        Food(
            foodId = 1,
            name = "Nasi Goreng",
            calories = 500,
            category = "Makanan",
            sugar = 10,
            protein = 20,
            carb = 30,
            fat = 5,
            desc = "Nasi goreng dengan rempah-rempah pilihan",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 2,
            name = "Es Teh Manis",
            calories = 50,
            category = "Minuman",
            sugar = 25,
            protein = 0,
            carb = 5,
            fat = 0,
            desc = "Es teh manis segar dengan pilihan rasa",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 3,
            name = "Spaghetti Bolognese",
            calories = 600,
            category = "Makanan",
            sugar = 15,
            protein = 30,
            carb = 45,
            fat = 15,
            desc = "Spaghetti dengan saus daging yang gurih",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 4,
            name = "Sushi",
            calories = 300,
            category = "Makanan",
            sugar = 5,
            protein = 15,
            carb = 40,
            fat = 10,
            desc = "Sushi dengan ikan segar dan sayuran",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 5,
            name = "Ayam Penyet",
            calories = 450,
            category = "Makanan",
            sugar = 8,
            protein = 25,
            carb = 35,
            fat = 20,
            desc = "Ayam goreng penyet dengan sambal pedas",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 6,
            name = "Rendang",
            calories = 700,
            category = "Makanan",
            sugar = 7,
            protein = 40,
            carb = 20,
            fat = 40,
            desc = "Daging sapi yang dimasak dengan rempah-rempah",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 7,
            name = "Kopi",
            calories = 5,
            category = "Minuman",
            sugar = 0,
            protein = 0,
            carb = 1,
            fat = 0,
            desc = "Kopi hitam tanpa gula",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 8,
            name = "Pizza Margherita",
            calories = 300,
            category = "Makanan",
            sugar = 5,
            protein = 12,
            carb = 36,
            fat = 12,
            desc = "Pizza dengan saus tomat dan keju mozzarella",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 9,
            name = "Pasta Aglio e Olio",
            calories = 400,
            category = "Makanan",
            sugar = 2,
            protein = 10,
            carb = 60,
            fat = 15,
            desc = "Pasta dengan bawang putih dan minyak zaitun",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 10,
            name = "Tahu Tempe",
            calories = 200,
            category = "Makanan",
            sugar = 5,
            protein = 15,
            carb = 10,
            fat = 10,
            desc = "Tahu dan tempe goreng yang renyah",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 11,
            name = "Salad Buah",
            calories = 150,
            category = "Makanan",
            sugar = 20,
            protein = 2,
            carb = 30,
            fat = 1,
            desc = "Campuran berbagai buah segar",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 12,
            name = "Teh Hijau",
            calories = 0,
            category = "Minuman",
            sugar = 0,
            protein = 0,
            carb = 0,
            fat = 0,
            desc = "Teh hijau segar tanpa gula",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 13,
            name = "Bubur Ayam",
            calories = 350,
            category = "Makanan",
            sugar = 3,
            protein = 20,
            carb = 50,
            fat = 10,
            desc = "Bubur nasi dengan ayam suwir dan kecap",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 14,
            name = "Kue Cubit",
            calories = 250,
            category = "Makanan",
            sugar = 20,
            protein = 5,
            carb = 30,
            fat = 8,
            desc = "Kue manis berbahan dasar tepung dan gula",
            photo = R.drawable.placeholder_image
        ),
        Food(
            foodId = 15,
            name = "Jus Jeruk",
            calories = 120,
            category = "Minuman",
            sugar = 24,
            protein = 2,
            carb = 30,
            fat = 0,
            desc = "Jus segar dari jeruk pilihan",
            photo = R.drawable.placeholder_image
        )
    )





}