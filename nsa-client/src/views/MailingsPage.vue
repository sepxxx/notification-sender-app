<template>
  <div class="fullpage">
    <MailingReportCard> </MailingReportCard>
    <MailingReportCard> </MailingReportCard>
    <MailingReportCard> </MailingReportCard>
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