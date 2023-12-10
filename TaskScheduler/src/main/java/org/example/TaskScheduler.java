package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class TaskScheduler {
    public static void main(String[] args) {
        var taskB = new Task("B", 3);
        var taskC = new Task("C", 3);
        var taskA = new Task("A", 1);
        taskA.addDependents(taskB, taskC);
        var taskD = new Task("D", 4);
        taskD.addDependents(taskC);
        var taskE = new Task("E", 2);
        taskE.addDependents(taskB, taskD);
        var taskF = new Task("F", 1);

        var tasks = List.of(taskB, taskC, taskD, taskA, taskE, taskF);
        System.out.printf("\nShortest completion time : %d time units\n", findCompletionTime(tasks));
    }

    private static int findCompletionTime(Collection<Task> tasks) {
        int time = 0;
        // Task cache for faster reference
        var taskCache = new HashMap<String, Task>();

        // Dependency graph for a Task.
        var dependency = new HashMap<String, List<String>>();

        // Building the dependency graph based on the tasks that are provided.
        tasks.parallelStream().forEach(task -> {
            var parentId = task.getId();
            if (!dependency.containsKey(parentId)) {
                dependency.put(parentId, new LinkedList<>());
            }
            taskCache.put(task.getId(), task);
            var dependents = task.getDependents();
            if (!dependents.isEmpty()) {
                for (var dependent : dependents) {
                    var childId = dependent.getId();
                    var parents = dependency.get(childId);
                    if (parents == null) {
                        parents = new LinkedList<>();
                    }
                    parents.add(parentId);
                    dependency.put(childId, parents);
                }
            }
        });

        //Traversing the dependency and processing the task.
        var completed = new HashSet<String>();
        var iterationCount = 0;
        while(!dependency.isEmpty()) {
            var iterationCompletedTasks = new HashSet<String>();
            System.out.printf("\nIteration %d : %s\n", ++iterationCount, dependency);
            int maxDuration = 0;
            for (var entry : dependency.entrySet()) {
                var current = entry.getKey();
                var parents = entry.getValue();
                var currentComplete = true;

                if (!parents.isEmpty()) {
                    for (var parent : parents) {
                        if (!completed.contains(parent)) {
                            currentComplete = false;
                            break;
                        }
                    }
                }

                if (currentComplete) {
                    iterationCompletedTasks.add(current);
                    completed.add(current);
                    int currentDuration = taskCache.get(current).getDuration();
                    // Send current task to processing unit.
                    if (currentDuration >= maxDuration) {
                        maxDuration = currentDuration;
                    }
                }
            }
            System.out.printf("Took %d time units to process %s.\n", maxDuration, iterationCompletedTasks);
            for (var completedTask : iterationCompletedTasks) {
                dependency.remove(completedTask);
            }
            time += maxDuration;
            iterationCompletedTasks.clear();
        }
        return time;
    }
}