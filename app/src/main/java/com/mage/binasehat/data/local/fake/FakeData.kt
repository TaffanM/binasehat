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

    val fakeMuscleData = listOf(
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
            foodId = 4,
            name = "Anggur",
            calories = 69,  // Per 100g
            category = "Buah",
            sugar = 15.5f,  // grams
            protein = 0.7f,  // gram
            carb = 18,  // grams (rounded from 18.1)
            fat = 0.2f,  // grams
            headline = "Anggur segar, bahan alami kaya antioksidan yang mendukung kesehatan tubuh",
            desc = "Anggur adalah buah yang kaya antioksidan, sangat populer sebagai camilan atau dibuat jus.",
            photo = R.drawable.anggur
        ),
        Food(
            foodId = 5,
            name = "Apel",
            calories = 52,  // Per 100g
            category = "Buah",
            sugar = 10.4f,  // grams
            protein = 0.3f,  // grams
            carb = 14,  // grams (rounded from 13.8)
            fat = 0.2f,  // grams
            headline = "Apel segar, bahan alami yang kaya serat dan vitamin untuk camilan sehat",
            desc = "Apel adalah buah segar yang kaya akan serat dan vitamin. Cocok untuk camilan sehat atau sebagai bahan tambahan dalam salad.",
            photo = R.drawable.apel
        ),
        Food(
            foodId = 6,
            name = "Ayam-goreng",
            calories = 330,  // Per 100g fried chicken (average)
            category = "Makanan",
            sugar = 0.0f,  // grams
            protein = 27.0f,  // grams
            carb = 10,  // grams (rounded from 10.0)
            fat = 22.0f,  // grams
            headline = "Ayam goreng, pilihan populer yang mengandung protein dan lemak tinggi",
            desc = "Ayam goreng adalah makanan yang dimasak dengan cara digoreng, sering menjadi favorit banyak orang.",
            photo = R.drawable.ayam_goreng
        ),
        Food(
            foodId = 7,
            name = "Ayam-kampung",
            calories = 165,  // Per 100g (roasted)
            category = "Makanan",
            sugar = 0.0f,  // grams
            protein = 30.0f,  // grams
            carb = 0,  // grams
            fat = 6.0f,  // grams
            headline = "Ayam kampung, sumber protein tinggi dengan rasa yang lebih alami dan lezat",
            desc = "Ayam kampung adalah ayam yang dibesarkan secara alami, memiliki daging yang lebih keras dan rasa yang khas.",
            photo = R.drawable.ayam_kampung
        ),
        Food(
            foodId = 8,
            name = "Daging",
            calories = 250,  // Per 100g of cooked lean beef
            category = "Makanan",
            sugar = 0.0f,  // grams
            protein = 26.0f,  // grams
            carb = 0,  // grams
            fat = 17.0f,  // grams
            headline = "Daging sapi segar, sumber protein tinggi yang bisa disesuaikan dengan kebutuhan nutrisi harian",
            desc = "Daging adalah sumber protein hewani yang juga kaya akan vitamin dan mineral. Bisa dipanggang, direbus, atau ditumis.",
            photo = R.drawable.daging
        ),
        Food(
            foodId = 1,
            name = "Nasi Putih",
            calories = 130,  // Per 100g of cooked white rice
            category = "Makanan",
            sugar = 0.0f,  // grams
            protein = 2.7f,  // grams
            carb = 28,  // grams (no rounding necessary)
            fat = 0.3f,  // grams
            headline = "Beras putih, bahan dasar karbohidrat utama yang memberi energi jangka panjang",
            desc = "Nasi adalah sumber karbohidrat utama di banyak negara Asia, dibuat dengan cara memasak beras hingga matang. Biasanya disajikan sebagai pendamping makanan utama.",
            photo = R.drawable.nasi
        ),
        Food(
            foodId = 9,
            name = "Pasta",
            calories = 371,  // Per 100g of cooked spaghetti
            category = "Makanan",
            sugar = 3.0f,  // grams
            protein = 13.0f,  // grams
            carb = 74,  // grams (no rounding necessary)
            fat = 1.5f,  // grams
            headline = "Pasta sebagai bahan utama kaya karbohidrat kompleks, sempurna untuk memberi energi",
            desc = "Mie atau pasta adalah makanan berbahan dasar tepung yang digulung dan dipotong sesuai jenis. Dikenal dengan hidangan seperti spaghetti atau mie goreng.",
            photo = R.drawable.pasta
        ),
        Food(
            foodId = 10,
            name = "Pisang",
            calories = 89,  // Per 100g
            category = "Buah",
            sugar = 12.2f,  // grams
            protein = 1.1f,  // grams
            carb = 23,  // grams (rounded from 22.8)
            fat = 0.3f,  // grams
            headline = "Pisang matang, sumber kalium yang membantu keseimbangan elektrolit tubuh",
            desc = "Pisang adalah buah tropis yang kaya kalium dan serat. Biasanya dikonsumsi langsung atau digunakan dalam smoothie.",
            photo = R.drawable.pisang
        ),
        Food(
            foodId = 3,
            name = "Roti",
            calories = 265,  // Per 100g of white bread
            category = "Makanan",
            sugar = 5.0f,  // grams
            protein = 9.0f,  // grams
            carb = 49,  // grams (no rounding necessary)
            fat = 3.0f,  // grams
            headline = "Kontrol asupan karbohidrat dan kalori dengan bahan dasar roti yang kaya energi",
            desc = "Roti adalah makanan pokok yang terbuat dari tepung terigu, air, dan bahan pengembang. Biasanya digunakan untuk sarapan atau camilan.",
            photo = R.drawable.roti
        ),
        Food(
            foodId = 11,
            name = "Sayur",
            calories = 80,  // Per 100g (general mixed vegetables)
            category = "Makanan",
            sugar = 5.0f,  // grams
            protein = 3.0f,  // grams
            carb = 15,  // grams (rounded from 15.0)
            fat = 1.0f,  // gram
            headline = "Sayuran campur segar, bahan utama yang kaya vitamin dan mineral untuk mendukung diet seimbang",
            desc = "Sayur campur adalah kombinasi berbagai jenis sayuran yang dimasak bersama, memberikan berbagai macam vitamin dan mineral yang dibutuhkan tubuh.",
            photo = R.drawable.sayur_campur
        ),
        Food(
            foodId = 12,
            name = "Tahu",
            calories = 76,  // Per 100g of firm tofu
            category = "Makanan",
            sugar = 0.7f,  // grams
            protein = 8.0f,  // grams
            carb = 2,  // grams (rounded from 1.9)
            fat = 4.8f,  // grams
            headline = "Tahu, bahan nabati kaya protein dan serat, ideal untuk diet rendah kalori dan vegetarian",
            desc = "Tahu adalah produk olahan kedelai yang kaya protein dan serat, sering digunakan dalam masakan Asia seperti tumis atau sebagai bahan makanan vegetarian.",
            photo = R.drawable.tahu
        ),
        Food(
            foodId = 13,
            name = "Telur",
            calories = 155,  // Per large egg (50g)
            category = "Makanan",
            sugar = 1.1f,  // grams
            protein = 13.0f,  // grams
            carb = 1,  // grams (rounded from 1.1)
            fat = 10.6f,  // grams
            headline = "Sumber protein hewani yang ideal untuk diet seimbang dan pemulihan otot",
            desc = "Telur adalah sumber protein hewani yang kaya nutrisi. Dapat dimasak dengan berbagai cara, seperti digoreng, direbus, atau dibuat omelet.",
            photo = R.drawable.telur
        ),
        Food(
            foodId = 14,
            name = "Tempe",
            calories = 192,  // Per 100g
            category = "Makanan",
            sugar = 0.5f,  // grams
            protein = 19.0f,  // grams
            carb = 15,  // grams (no rounding necessary)
            fat = 10.0f,  // grams
            headline = "Tempe, sumber protein nabati fermentasi yang tinggi serat dan cocok untuk menu sehat sehari-hari",
            desc = "Tempe adalah hasil fermentasi kedelai yang kaya akan protein dan serat, cocok dijadikan alternatif sumber protein nabati dalam makanan vegetarian.",
            photo = R.drawable.tempe
        )
    )
}