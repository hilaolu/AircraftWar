package application

import com.github.tototoshi.csv.CSVReader

import java.io.File
import java.io.FileNotFoundException
import com.github.tototoshi.csv.CSVWriter

trait DAO {
    def getAll(): List[List[String]]
    def getOne(index: Int): List[String]
    def insert(s: List[String])
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

    def insert(query: List[String]) = {
        CSVDriver.insert(query)
    }

    def print() = {
        // CSVDriver.getAll().zipWithIndex.foreach(println)
    }

    def delete(index: Int) = {
        CSVDriver.delete(index)
    }

    def getAll(): List[List[String]] = {
        CSVDriver.getAll()
    }
}
