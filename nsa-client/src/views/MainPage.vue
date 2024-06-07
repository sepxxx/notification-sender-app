<template>
  <div class="fullpage">
    <!-- Main -->
    <el-row type="flex">
      <el-col :span="3">
        <div class="zaglavie">Обзор</div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="5">
        <div class="main__card main__card--promotion">
          <p>СОЗДАТЬ</p>
          <p>РАССЫЛКУ</p>
          <i class="el-icon-s-promotion"></i>
        </div>
      </el-col>
      <el-col :span="5">
        <div class="main__card main__card--upload">
          <p>ИМПОРТИРОВАТЬ</p>
          <p>КОНТАКТЫ</p>
          <i class="el-icon-upload"></i>
        </div>
      </el-col>

    </el-row>
    <!-- <el-row>
      <StatBar></StatBar>
    </el-row> -->
    <el-row>
      <div styles="height:500px; background-color:blue;">GO</div>
    </el-row>
    <el-row>
      <div styles="height:500px; background-color:blue;">GO</div>
    </el-row>
  </div>
</template>

<script>
// import StatBar from '../components/StatBar.vue'
export default {
  name: 'MainPage',
  components: {
    // StatBar
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
