package proba
import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.http.Predef._

object Feeders {
  val myLogin: BatchableFeederBuilder[String] = csv("login.dat",',').random.eager
}
