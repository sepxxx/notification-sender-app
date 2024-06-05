<template>
  <!-- <p>HELOOOOu</p> -->
  <div class="fullpage">
    <el-container>
      <el-header>Header</el-header>

      <el-container>
          <el-aside width="200px" class="container__aside">
          <el-menu default-active="2" class="el-menu-vertical-demo">
            <el-menu-item index="1">
              <i class="el-icon-menu"></i>
              <span>ДАШБОРД</span>
            </el-menu-item>
            <el-menu-item index="2">
              <i class="el-icon-menu"></i>
              <span>СПИСКИ</span>
            </el-menu-item>
            <el-menu-item index="3">
              <i class="el-icon-menu"></i>
              <span>РАССЫЛКИ</span>
            </el-menu-item>
            <el-menu-item index="3">
              <i class="el-icon-menu"></i>
              <span>СОЗДАТЬ РАССЫЛКУ</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>Main</el-main>
      </el-container>
    </el-container>

  </div>

</template>

<script>
export default {
  name: 'MainPage',
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  /* .container__aside { */
    /* height: 100%; */
  /* } */
  /* .full-page {
    height: 100%;
    width: 100%;
  } */
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
</style>


