<template>
  <div class="fullpage">
    <MailingReportCard
        v-for="task in tasks"
        :key="task.id"
        :id="task.id"
        :listName="task.listName"
        :createdAt="task.createdAt"
        :text="task.text"
        :list-total="task.listTotal"
        :task-stat="task.taskStat"
    ></MailingReportCard>
  </div>
</template>


<script>
import MailingReportCard from '../components/MailingReportCard.vue'
export default {
  name: 'MailingsPage',
  components: {
    MailingReportCard
  },
  data() {
    return  {
      tasks: [],
    }
  },
  methods: {
    async getTasks() {
      const tasks = await this.$taskResolverService.getTasks();
      // tasks.forEach(
      //     task -> {task.taskStat = await this.$notificationSenderService.getTaskStat(task.id)};
      // )
      const updatedTasks = await Promise.all(tasks.map(
          async task => {
            task.taskStat = await this.$notificationSenderService.getTaskStat(task.id);
            return task;
          }
      ));
      this.tasks = updatedTasks;
      console.log(tasks)
    }
  },
  mounted() {
    this.getTasks();
  }
}
</script>

<style scoped>
  /* .fullpage {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  } */
</style>