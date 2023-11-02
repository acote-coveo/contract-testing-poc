package org.example

import clients.QuerySuggestClientImpl

import au.com.dius.pact.consumer.ConsumerPactBuilder
import au.com.dius.pact.core.model.RequestResponsePact
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import pact4s.dsl.ScalaDsl
import pact4s.scalatest.RequestResponsePactForger

class ScalaTestPact extends AnyFlatSpec with Matchers with RequestResponsePactForger with ScalaDsl {
  override def pact: RequestResponsePact = ConsumerPactBuilder
    .consumer("searchapi")
    .hasPactWith("ml-querysuggest")
    //     Get query suggest
    .`given`(
      "with suggestions",
      Map("count" -> 1)
    )
    .uponReceiving("Request to fetch query suggestions")
    .method("POST")
    .path("/querySuggest")
    .body("""{"query":"something","count":1}""")
    .willRespondWith()
    .status(200)
    .body(
      newJsonObject { rootObj =>
        rootObj.array("suggestions", arr => {
          arr.`object` { suggestion =>
            suggestion.stringType("expression", "foo")
            suggestion.numberType("score", 100.0)
          }
        })
      }
    )
    .`given`("without suggestion")
    .uponReceiving("Request fo fetch query suggestions with bad query")
    .method("POST")
    .path("/querySuggest")
    .body("""{"query":"-_-","count":1}""")
    .willRespondWith()
    .status(200)
    .body("""{"suggestions":[]}""")
    .toPact


    it should "return query suggestions" in {
      val sut = new QuerySuggestClientImpl(mockServer.getUrl)

      val response = sut.execute("something", 1)

      response should have length 1
    }

  it should "return an empty list" in {
    val sut = new QuerySuggestClientImpl(mockServer.getUrl)

    val response = sut.execute("-_-", 1)

    response should have length 0
  }
}
