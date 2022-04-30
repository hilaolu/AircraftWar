package application

trait Command {
    def execute()
}

object CornJob {
    def apply[T <: Command](command: T, time: Int) = {
        class Job extends Runnable {
            override def run() = {
                Thread.sleep(time)
                command.execute()
            }
        }

        new Thread(new Job).start
    }
}
