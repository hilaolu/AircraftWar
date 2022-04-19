package application

import com.github.tototoshi.csv.CSVReader

import java.io.File
import java.io.FileNotFoundException
import com.github.tototoshi.csv.CSVWriter

trait DAO {
    def getAll(): List[Score]
    def getOne(index: Int): Score
    def insert(s: Score)
    def delete(index: Int)
}

class Score(score: Int) {
    def Score2List(): List[String] = {
        List(score.toString())
    }

    override def toString: String = {
        score.toString()
    }
}

object Score {
    def List2Score(l: List[String]): Score = {
        new Score(l(0).toInt)
    }

}

object ScoreBoard {

    def init() = {
        CSVDriver.init()
    }

    def insert(score: Int) = {
        CSVDriver.insert(new Score(score))
    }

    def print() = {
        CSVDriver.getAll().zipWithIndex.foreach(println)
    }
}
