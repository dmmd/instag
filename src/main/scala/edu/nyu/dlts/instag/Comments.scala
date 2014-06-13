package edu.nyu.dlts.instag

import com.typesafe.config.ConfigFactory
import org.apache.http.impl.client.HttpClients
import java.util.UUID

object Comments extends App {
  val conf = ConfigFactory.load()
  val client = HttpClients.createDefault()
  val db = new Db(conf)
  val requests = new Requests(client, conf)

  val images = db.getAllImageIds
  
  images.foreach{image => 
    val imageUUID = image._1
    val imageId = image._2
    println(image._2)
    val map = requests.getCommentsByMediaId(imageId)
    if(map.size > 0){
      map.foreach{imageMap =>
  	imageMap("imageId") = imageUUID.toString
  	db.addComment(imageMap)
      }
    }
  }
}