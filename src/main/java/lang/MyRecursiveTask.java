package lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MyRecursiveTask extends RecursiveTask<Integer> {
   private int[] arr;

   private static final int THRESHOLD = 20;

   public MyRecursiveTask(int[] arr) {
      this.arr = arr;
   }

   @Override
   protected Integer compute() {
      if (arr.length > THRESHOLD) {
         return ForkJoinTask.invokeAll(createSubtasks())
                 .stream()
                 .mapToInt(ForkJoinTask::join)
                 .sum();
      } else {
         return processing(arr);
      }
   }

   private Collection<MyRecursiveTask> createSubtasks() {
      List<MyRecursiveTask> dividedTasks = new ArrayList<>();
      dividedTasks.add(new MyRecursiveTask(
              Arrays.copyOfRange(arr, 0, arr.length / 2)));
      dividedTasks.add(new MyRecursiveTask(
              Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
      return dividedTasks;
   }

   private Integer processing(int[] arr) {
      return Arrays.stream(arr)
              .filter(a -> a > 10 && a < 27)
              .map(a -> a * 10)
              .sum();
   }
}