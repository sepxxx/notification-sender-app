<template>
  <div id="app">
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="5">
            <div class="logo-block">
              <i class="el-icon-umbrella icon"></i>
              <h1>UMISENDER</h1>
            </div>
          </el-col>

          <el-col :span="3" :offset="16">
              <div>
                <i class="el-icon-user icon"></i>
              </div>
          </el-col>
        </el-row>
      </el-header>

      <el-container>
        <el-aside width="200px" class="container__aside">
          <el-menu default-active="2" class="el-menu-vertical-demo">
            <router-link to="/">
              <el-menu-item index="1">
              <i class="el-icon-menu"></i>
              <span>ДАШБОРД</span>
            </el-menu-item>
            </router-link>

            <router-link to="/lists">
            <el-menu-item index="2">
              <i class="el-icon-menu"></i>
              <span>СПИСКИ</span>
            </el-menu-item>
          </router-link>

            <router-link to="/mailings">
            <el-menu-item index="3">
              <i class="el-icon-menu"></i>
              <span>РАССЫЛКИ</span>
            </el-menu-item>
          </router-link>

            <el-menu-item index="4">
              <i class="el-icon-menu"></i>
              <span>СОЗДАТЬ РАССЫЛКУ</span>
            </el-menu-item>
          </el-menu>
        </el-aside>


        <el-main>
          <router-view/>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>

<script>

export default {
  name: 'App',
  // components: {

  // }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.container__aside {
  /* background-color: #fafbfc; */
  border-radius: 24px;
}

.el-header,
.el-footer {
  /* background-color: #B3C0D1; */
  background-color: white;
  /* color: #333; */
  text-align: center;
  line-height: 60px;
}

.el-main {
  /* background-color: #E9EEF3; */
  /* color: #333; */
  background-color: #fafbfc;
  text-align: center;
  line-height: 160px;
  height: 100vh;
  border-radius: 24px;
}

body>.el-container {
  margin-bottom: 40px;
}

.icon {
  font-size: 50px;
}

.logo-block {
  display: flex;
  align-items: center;
}
.logo-block h1{
  margin: 0px;
  padding: 0px;
}

a {
  /* color: #00bfff; */
  text-decoration: none;
  cursor: pointer;
}

.el-menu-item {
  text-align: left;
  padding: 0 15px 0 15px;
}
</style>
