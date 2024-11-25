package io.apaaja.carbonsync.data

enum class DailyActivitiesCarbonReduction(val carbonReduction: Int) {
    PlantBasedMeal(3000),
    AirDrying(1500);

    companion object {
        fun listString(): List<String> {
            return listOf("Eat Plant Based Meal", "Air-Dry Clothes")
        }

        fun fromString(value: String): DailyActivitiesCarbonReduction? {
            return when (value) {
                "Eat Plant Based Meal" -> PlantBasedMeal
                "Air-Dry Clothes" -> AirDrying
                else -> null
            }
        }
    }
}