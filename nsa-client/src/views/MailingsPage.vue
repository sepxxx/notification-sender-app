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
      const resp = await this.$taskResolverService.getTasks();
      this.tasks = resp;
      console.log(resp)
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