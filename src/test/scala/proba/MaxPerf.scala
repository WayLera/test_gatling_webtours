package proba

import io.gatling.core.Predef._
import scala.concurrent.duration._
import scala.language.postfixOps

class MaxPerf extends Simulation {
  val intensity: Int = 500//9000
  val stagesNumber: Int = 1
  val stageDuration: FiniteDuration = 5 minute
  val rampDuration: FiniteDuration = 1 minute
  val testDuration: FiniteDuration = 5 minute

  setUp(
    obj_scenario().inject(
         incrementUsersPerSec((intensity / stagesNumber).toInt) // интенсивность на ступень
        .times(stagesNumber) // Количество ступеней stagesNumber
        .eachLevelLasting(stageDuration) // Длительность полки
        .separatedByRampsLasting(rampDuration) // Длительность разгона
        .startingFrom(0) // Начало нагрузки с 0
    )
  ).protocols(
    proba.httpProtocol_wt
  )
    .maxDuration(testDuration)
    .assertions(global.responseTime.max.lt(10000))

}