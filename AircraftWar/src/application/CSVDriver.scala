package application

import com.github.tototoshi.csv.CSVReader

import java.io.File
import java.io.FileNotFoundException
import com.github.tototoshi.csv.CSVWriter

object CSVDriver extends DAO {
    val f = new File("score.csv");

    def init() = {
        if (f.isDirectory()) {
            assert(false)
        }
        if (!f.exists()) {
            f.createNewFile()
        }
    }

    def insert(score: Int) = {}

    def delete(index: Int): Unit = {}

    def getAll(): List[Score] = {
        val reader = CSVReader.open(f)
        val list = reader.all().sortWith(_(0).toInt > _(0).toInt)
        list.map(l => Score.List2Score(l))
    }

    def getOne(index: Int): Score = {
        val reader = CSVReader.open(f)
        val list = reader.all().sortWith(_(0).toInt > _(0).toInt)
        list.map(l => Score.List2Score(l))(index)
    }

    def insert(s: Score): Unit = {
        val writer = CSVWriter.open(f, append = true)
        writer.writeRow(s.Score2List)
        writer.close()
    }
}
