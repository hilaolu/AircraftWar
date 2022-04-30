package application

import com.github.tototoshi.csv.CSVReader

import java.io.File
import java.io.FileNotFoundException
import com.github.tototoshi.csv.CSVWriter

import scala.collection.mutable.ArrayBuffer

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

    def delete(index: Int): Unit = {
        var new_list = getAll().toArray.toBuffer
        new_list.remove(index)
        // what if list empty?
        val writer = CSVWriter.open(f, append = false)
        writer.writeAll(new_list.toList)
        // writer.writeAll(List(List("a", "b", "c"), List("d", "e", "f")))
        writer.close()
    }

    def getAll(): List[List[String]] = {
        val reader = CSVReader.open(f)
        val list = reader.all()
        reader.close()
        list
    }

    def getOne(index: Int): List[String] = {
        val reader = CSVReader.open(f)
        val list = reader.all().sortWith(_(0).toInt > _(0).toInt)
        reader.close()
        list(index)
        // list
    }

    def insert(s: List[String]): Unit = {
        val writer = CSVWriter.open(f, append = true)
        writer.writeRow(s)
        writer.close()
    }
}
