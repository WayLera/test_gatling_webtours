package proba
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import scala.util.Random

object obj_scenario{
  def apply(): ScenarioBuilder = new scenario().scn
}

class scenario {
  val tran_authorize: ChainBuilder = group("TRAN_authorize"){
      exec(Action.request_3_session)
      //.pause(2)
      .exec(Action.request_5_autoriz)
  }
  val tran_buy: ChainBuilder = group("TRAN_buy"){
       exec(Action.request_15_to_fly)
      //.pause(2)
      .exec(Action.request_16_get_reservations)
      .exec(session => {
        val arr_Reservations = session("arrReservations").as[List[String]]
        val count_Reservations = arr_Reservations.length/2
        val idx_depart = Random.nextInt(count_Reservations)
        var idx_arrive = Random.nextInt(count_Reservations)
        while(idx_arrive==idx_depart) {
          idx_arrive = Random.nextInt(count_Reservations)
        }
        //println(idx_depart.toString)
        //println(idx_arrive.toString)
        val depart = arr_Reservations(idx_depart)
        val arrive = arr_Reservations(idx_arrive)
        session.set("depart", depart).set("arrive", arrive)
      })
      .exec(session => {
        println(session)
        session
      })
      //.pause(3)
      .exec(Action.request_21_set_param_fly)
      .exec(session => {
        val arr_Outbound = session("arrOutbound").as[List[String]]
        val count_Outbound = arr_Outbound.length
        val idx_outbound = Random.nextInt(count_Outbound)
        //println(count_Outbound.toString)
        //println(idx_outbound.toString)
        val outbound = arr_Outbound(idx_outbound)
        session.set("outbound", outbound)
      })
      .exec(session => {
        println(session)
        session
      })
         .exec(Action.request_22_set_param_fly_ex)
        // .pause(3)
         .exec(Action.request_23_set_param_pass)
  }
  val scn: ScenarioBuilder = scenario("scenario")
    .feed(Feeders.myLogin)
    .exec(Action.request_0_to_wt)
    .pause(2)
    .exec(tran_authorize)
    .pause(4)
    .exec(tran_buy)
    .pause(8)
    .exec(Action.request_24_to_menu)
}
