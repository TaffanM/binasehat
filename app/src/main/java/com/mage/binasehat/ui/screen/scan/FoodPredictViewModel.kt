package com.mage.binasehat.ui.screen.scan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mage.binasehat.R
import com.mage.binasehat.data.model.Food
import com.mage.binasehat.data.remote.response.FoodScanResponse
import com.mage.binasehat.data.util.UiState
import com.mage.binasehat.repository.PredictFoodRepository
import com.mage.binasehat.ui.screen.food.FoodViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FoodPredictViewModel @Inject constructor(
    private val predictFoodRepository: PredictFoodRepository,
    private val foodViewModel: FoodViewModel
) : ViewModel() {

    private val foodList = listOf(
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

    private val _predictResult = MutableStateFlow<UiState<FoodScanResponse>>(UiState.Loading)
    val predictResult: StateFlow<UiState<FoodScanResponse>> = _predictResult.asStateFlow()

    fun predictImage(file: File) {
        viewModelScope.launch {
            try {
                predictFoodRepository.predict(file)
                    .collect { result ->
                        _predictResult.value = result

                        if (result is UiState.Success) {
                            val predictedClassName = result.data.predictedClass.trim()

                            // Find matching food from predefined list
                            val predictedFood = foodList.find { food ->
                                food.name.equals(predictedClassName, ignoreCase = true)
                            }

                            if (predictedFood != null) {
                                Log.d("FoodPredictViewModel", "Adding predicted food to cart: ${predictedFood.name}")
                                foodViewModel.addItem(predictedFood, 1)
                            } else {
                                Log.e("FoodPredictViewModel", "Predicted food not found: $predictedClassName")
                                _predictResult.value = UiState.Error("Makanan '$predictedClassName' tidak ditemukan dalam daftar.")
                            }
                        }
                    }
            } catch (e: Exception) {
                Log.e("FoodPredictViewModel", "Error predicting food", e)
                _predictResult.value = UiState.Error(e.localizedMessage ?: "Terjadi kesalahan")
            }
        }
    }
}

