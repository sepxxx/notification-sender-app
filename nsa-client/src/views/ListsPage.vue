<template>
  <div class="fullpage">
    <el-row class="row-zaglavie">
      <el-col :span="6">
        <div class="zaglavie-block">
          <span class="zaglavie">Списки</span>
          <span>Списки - 3 • Контакты в списках - 4</span>
        </div>
      </el-col>
      <el-col :span="4" :offset="14">
        <el-button type="primary">Upload<i class="el-icon-upload el-icon-right"></i></el-button>
      </el-col>
    </el-row>
  </div>
</template>


<script>

export default {
  name: 'ListsPage',
  components: {
    
  }
}
</script>

<style scoped>
  .row-zaglavie {
    background-color: white;
    border-radius: 24px;
    display: flex;
    align-items: center;
  }

  .zaglavie-block {
    display: flex;
    flex-direction: column;
    line-height: 100%;
  }

  .zaglavie-block span{
    padding: 2px 0px;
  }

</style>