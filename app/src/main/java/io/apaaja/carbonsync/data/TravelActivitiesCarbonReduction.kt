package io.apaaja.carbonsync.data

enum class TravelActivitiesCarbonReduction(val carbonReductionPerKilometer: Int) {
    TravelByCar(0),
    TravelByMotorbike(57),
    TravelByBus(74),
    TravelByElectricCar(124),
    TravelByTrain(136);

    companion object {
        fun listString(): List<String> {
            return listOf("Car", "Motorbike", "Bus", "Electric Car", "Train")
        }

        fun fromString(value: String): TravelActivitiesCarbonReduction? {
            return when (value) {
                "Car" -> TravelByCar
                "Motorbike" -> TravelByMotorbike
                "Bus" -> TravelByBus
                "Electric Car" -> TravelByElectricCar
                "Train" -> TravelByTrain
                else -> null
            }
        }
    }
}