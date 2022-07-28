for ID in {1..10}
do
    java -jar target/mtask-0.0.1-SNAPSHOT.jar --spring.cloud.task.name=task-app-$ID> result$ID.txt
done


