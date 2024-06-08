<template>
  <div class="fullpage">
    <!-- Main -->
    <el-row class="row-zaglavie">
      <el-col :span="6">
        <div class="zaglavie-block">
          <span class="zaglavie">Обзор</span>
          <span>Начните свою первую рассылку прямо сейчас!</span>
        </div>
      </el-col>

    </el-row>
    <el-row :gutter="20">
      <el-col :span="4" >
        <router-link to="/mailings/creation">
          <div class="main__card main__card--promotion">
            <p>СОЗДАТЬ</p>
            <p>РАССЫЛКУ</p>
            <i class="el-icon-s-promotion"></i>
          </div>
        </router-link>
      </el-col>
      <el-col :span="4">
        <router-link to="/lists">
          <div class="main__card main__card--upload">
            <p>ИМПОРТИРОВАТЬ</p>
            <p>КОНТАКТЫ</p>
            <i class="el-icon-upload"></i>
          </div>
        </router-link>
      </el-col>

      <el-col :span="14" >
        <el-carousel :interval="1000" type="card" height="240px" width="450px">
          <el-carousel-item v-for="(image, index) in images" :key="index">
            <img :src="require(`@/assets/${image}`)" class="image">
          </el-carousel-item>
        </el-carousel>
      </el-col>

    </el-row>
    <el-row class="row-zaglavie">
      <el-col :span="6">
        <div class="last-mailing-block">
          <span class="zaglavie">Последняя рассылка</span>
        </div>
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
  },
  data() {
    return {
      images: [
        'ab-testy.png',
          'detalnaya-statistika.png',
          'gibkie-integracii.png',
          'vsplyvaushchie-formy.png'
      ]
    }
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
  background-color: rgba(135, 40, 118, 0.8);
}
.main__card--upload {
  background-color: rgba(29, 183, 173, 0.8);
}

/*
.main__card--promotion {
  background-color: #852876;
}

.main__card--upload {
  background-color: #1db7ad;
}
 */

.last-mailing-block {
  line-height: 80px;
}

/* .obzor {
  font-weight: 500;
  color: black;
  font-size: 32px;
} */
</style>
