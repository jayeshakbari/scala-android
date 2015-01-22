package com.fortysevendeg.scala.android.ui.akkasimon.fragments

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup}
import android.widget.Button
import com.fortysevendeg.scala.android.ui.akkasimon.Styles._
import com.fortysevendeg.scala.android.ui.akkasimon.actors.ComputerActor.ClickedUserColor
import com.fortysevendeg.scala.android.ui.akkasimon.util.SimonAkkaFragment
import macroid.FullDsl._
import macroid._

import scala.concurrent.ExecutionContext.Implicits.global

class ColorFragment extends SimonAkkaFragment {

  lazy val actorName = getArguments.getString("name")
  lazy val color = getArguments.getInt("color")

  lazy val actor = Some(actorSystem.actorSelection(s"/user/$actorName"))

  var simonColor = slot[Button]

  def receive = lightColor(color)

  def lightColor(c: Int = color) = simonColor <~ vAlpha(1f) <~~ delay(600) <~ simonButton(c) <~~ delay(600)

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = getUi {
    w[Button] <~ wire(simonColor) <~ simonButton(color) <~ On.click(lightColor() ~~
        Ui(computerActor ! ClickedUserColor(color)))
  }
}