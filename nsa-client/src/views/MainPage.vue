<template>
  <div class="fullpage">
    <!-- Main -->
    <el-row class="row-zaglavie">
      <el-col :span="3">
        <div class="zaglavie-block">
          <span class="zaglavie">Обзор</span>
        </div>
      </el-col>
      <el-col :span="9">
        <span>Начните свою первую рассылку прямо сейчас!</span>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="4" >
        <div class="main__card main__card--promotion">
          <p>СОЗДАТЬ</p>
          <p>РАССЫЛКУ</p>
          <i class="el-icon-s-promotion"></i>
        </div>
      </el-col>
      <el-col :span="4">
        <div class="main__card main__card--upload">
          <p>ИМПОРТИРОВАТЬ</p>
          <p>КОНТАКТЫ</p>
          <i class="el-icon-upload"></i>
        </div>
      </el-col>

      <el-col :span="8" offset="8">
        <el-carousel>
          <el-carousel-item v-for="item in 4" :key="item">
            <h3 class="small">{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
      </el-col>

    </el-row>
    <el-row class="row-zaglavie">
      <el-col :span="9">
          <span>Выберите шаблон и используйте прошлые рассылки или начните с нуля.</span>
      </el-col>
    </el-row>

    <MailingReportCard></MailingReportCard>
  </div>
</template>

<script>
// import StatBar from '../components/StatBar.vue'
import MailingReportCard from "@/components/MailingReportCard.vue";

export default {
  name: 'MainPage',
  components: {
    MailingReportCard,
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.main__card {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 5px;
  margin: 5px;
  width: 200px;
  height: 300px;
  border-radius: 5%;
  color: white;
  line-height: 100%;
}

.main__card i {
  font-size: 50px;
}

.main__card:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
}

.main__card--promotion {
  background-color: #852876;
}

.main__card--upload {
  background-color: #1db7ad;
}

/* .obzor {
  font-weight: 500;
  color: black;
  font-size: 32px;
} */
</style>
