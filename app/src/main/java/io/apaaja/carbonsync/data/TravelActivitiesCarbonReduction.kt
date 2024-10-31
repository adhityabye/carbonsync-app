package io.apaaja.carbonsync.data

enum class TravelActivitiesCarbonReduction(val carbonReductionPerKilometer: Int) {
    TravelByCar(0),
    TravelByMotorbike(57),
    TravelByBus(74),
    TravelByElectricCar(124),
    TravelByTrain(136)
}