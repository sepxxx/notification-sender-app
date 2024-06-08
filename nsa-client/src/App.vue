<template>
  <div id="app">
    <el-container class="header-container">
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

      <el-container class="main-container">
        <el-aside width="200px" class="main-container__aside">
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
              <span>ИСТОРИЯ</span>
            </el-menu-item>
          </router-link>

          <router-link to="/mailings/creation">
            <el-menu-item index="4">
              <i class="el-icon-menu"></i>
              <span>РАССЫЛКА</span>
            </el-menu-item>
          </router-link>

          <router-link to="/templates/offers">
            <el-menu-item index="5">
              <i class="el-icon-menu"></i>
              <span>ПРЕДЛОЖЕНИЯ</span>
            </el-menu-item>
          </router-link>

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
  /* font-family: "Inter", "PT Sans", Calibri, Helvetica, Arial, sans-serif; */
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  height: 100vh;
}
#app section {
  height: 100%;
  /* overflow: hidden; */
}
.main-container {
  height: 90%;
  /* overflow: hidden; */
}
.main-container__aside {
  border-radius: 24px;
}

.el-header,
.el-footer {
  background-color: white;
  text-align: center;
  line-height: 60px;
}

.el-main {
  background-color: #fafbfc;
  text-align: center;
  line-height: 160px;
  border-radius: 24px;
  /* overflow: hidden; */
}

body>.el-container {
  margin-bottom: 40px;
}

.el-container main {
  overflow: hidden;
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
  text-decoration: none;
  cursor: pointer;
}

.el-menu-item {
  text-align: left;
  /* padding: 0 15px 0 15px; */
}

.zaglavie {
  font-weight: 500;
  color: black;
  font-size: 32px;
}

.row-zaglavie {
  background-color: white;
  border-radius: 24px;
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.zaglavie-block {
  display: flex;
  flex-direction: column;
  line-height: 200%;
}

.zaglavie-block span {
  padding: 2px 0px;
}
</style>
