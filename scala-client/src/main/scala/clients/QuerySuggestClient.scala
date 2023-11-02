package org.example
package clients

import sttp.client4._
import sttp.client4.httpclient.HttpClientSyncBackend
import sttp.client4.json4s._


trait QuerySuggestClient {
  def execute(query: String, count: Int): Seq[QuerySuggestion]
}

class QuerySuggestClientImpl(url: String) extends QuerySuggestClient {
  private implicit val serialization = org.json4s.native.Serialization
  private implicit val formats = org.json4s.DefaultFormats

  override def execute(query: String, count: Int): Seq[QuerySuggestion] = {
    val request = basicRequest
      .body(QuerySuggestRequest(query,count))
      // use an optional parameter in the URI
      .post(uri"$url/querySuggest")
      .response(asJson[QuerySuggestResponse])

    val backend = HttpClientSyncBackend()
    val response = request.send(backend)

    response.body match {
      case Left(value) => throw new Exception("oops")
      case Right(value) => value.suggestions
    }
  }
}
