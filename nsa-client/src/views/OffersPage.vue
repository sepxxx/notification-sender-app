<template>
  <div class="fullpage">
    <el-row class="row-zaglavie">
      <el-col :span="6">
        <div class="zaglavie-block">
          <span class="zaglavie">Предложения шаблонов</span>
          <span>Выберите шаблон и он появится в вашей бибилотеке или откажитесь от него.</span>
        </div>
      </el-col>
    </el-row>

    <TemplateOfferCard
        v-for="templateOffer in taskTemplateOffers"
        :key="templateOffer.id"
        :text="templateOffer.text"
        :list-name="templateOffer.listName"
        :template-id="templateOffer.id"
    >
    </TemplateOfferCard>
  </div>

  



</template>


<script>

import TemplateOfferCard from "@/components/TemplateOfferCard.vue";

export default {
  name: 'OffersPage',
  components: {
    TemplateOfferCard
  },
  data() {
    return {
      taskTemplateOffers: [],
    }
  },
  methods: {
    async getTaskTemplateOffers() {
      try {
        const params = {"taskTemplateStatus": "AWAITS_ACTION"};
        const response = await this.$taskResolverService.getTaskTemplates(params);
        this.taskTemplateOffers = response;
        console.log(response)
      } catch (error) {
        console.error(error);
        this.$message({
          message: error.response.data.message,
          type: 'error'
        });
      }
    },
  },
  mounted() {
    this.getTaskTemplateOffers();
  }
}
</script>

<style scoped>


</style>