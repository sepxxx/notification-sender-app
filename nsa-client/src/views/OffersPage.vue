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

    <TemplateOfferCard></TemplateOfferCard>
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
    }
  }
}
</script>

<style scoped>


</style>