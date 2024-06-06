<template>
  <div class="fullpage">
      <!-- Main -->
      <el-row>
      <el-col :span="5">
        <div class="main__card">           
        <p>СОЗДАТЬ</p>
        <p>РАССЫЛКУ</p>
        <i class="el-icon-s-promotion" ></i>
      </div>
      </el-col>
      <el-col :span="5">
      <div class="main__card">
        <p>ИМПОРТИРОВАТЬ</p>
        <p>КОНТАКТЫ</p>
        <i class="el-icon-upload"></i>
      </div>
      </el-col>

    </el-row>
    <el-row>
      <StatBar></StatBar>
    </el-row>
  </div>
</template>

<script>
import StatBar from '../components/StatBar.vue'
export default {
  name: 'MainPage',
  components: {
    StatBar
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.main__card {
  /* display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center; */
  /* width: 25%; */
  /* height: 50%; */
  padding: 5px;
  margin: 5px;
  width: 200px;
  height: 300px;
  background-color:aliceblue;
  border-radius: 5%;
}

.main__card i {
  font-size: 50px;
  /* margin-bottom: 50px; */
  /* margin: 0; */
}

.main__card p {
  /* margin: 0; */
  height: 10%;
}

.main__card:hover {
  background-color: #f0f0f0;
}

.main__card:active {
  background-color: #e0e0e0;
}


</style>
