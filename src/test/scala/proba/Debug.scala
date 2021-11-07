package proba

import io.gatling.core.Predef._
//import io.gatling.http.Predef._

class Debug extends Simulation {

  setUp(
    obj_scenario().inject(atOnceUsers(1))
  ).protocols(
    proba.httpProtocol_wt
  )
    .maxDuration(20000)
    //.assertions(global.responseTime.max.lt(10000))


}