<template>
<div class="report-card">
      <el-row class="report-card__row" >
<!--        <el-col :span="3"><span>Id рассылки</span></el-col>-->
        <el-col :span="2">
          <span class="report-card___id-block"><i class="el-icon-message icon" ></i> ID: {{id}}</span>
        </el-col>


        <el-col :span="3" :offset="19">
          <div class="status-block">
            <span>Статус</span>
            <i class="el-icon-s-flag icon"></i>
          </div>
        </el-col>
      </el-row>
      <el-row class="report-card__row">
        <el-col :span="2">
          <div class="data-block">
            <i class="el-icon-s-promotion icon"></i>
            <span>Alertzy</span>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="data-block">
            <i class="el-icon-date icon"></i>
            <span>Создана: {{createdAt}}</span>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="data-block">
            <i class="el-icon-tickets icon"></i>
            <span>Список: {{listName}}</span>
          </div>
        </el-col>
      </el-row>

      <el-row class="report-card__row">
        <el-col :span="4">
          <div class="stat-block">
           <div class="icon-wrapper"><i class="el-icon-edit icon"></i></div>
           <div class="stat-block__info">
            <span>?%</span>
            <span>Отправлено</span>
           </div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="stat-block">
           <div class="icon-wrapper"><i class="el-icon-document-checked icon"></i></div>
           <div class="stat-block__info">
            <span>?%</span>
            <span>Доставлено</span>
           </div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="stat-block">
           <div class="icon-wrapper"><i class="el-icon-notebook-1 icon"></i></div>
           <div class="stat-block__info">
            <span>?%</span>
            <span>Прочитано</span>
           </div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="stat-block">
           <div class="icon-wrapper"><i class="el-icon-thumb icon"></i></div>
           <div class="stat-block__info">
            <span>?%</span>
            <span>Переходы</span>
           </div>
          </div>
        </el-col>
        <el-col :span="2" :offset="6"> <div class="icon-wrapper"><i class="el-icon-more-outline icon"></i></div></el-col>
      </el-row>
    </div>
</template>

<script>

export default {
  name: 'MailingReportCard',
  // components: {

  // },
  props: {
    id: null,
    listName: null,
    createdAt: null
  },
  data() {
      return {
      };
    },
}
</script>

<style scoped>
  .report-card {
    background-color: white;
    border-radius: 24px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    margin: 0 0 20px 0;
  }

  .report-card__row {
    line-height: 60px;
  }

  .report-card___id-block {
    display: flex;
    justify-content: space-between;
    align-items: center;
    line-height: 100%;
    padding: 4px 16px;
    background-color: #d2f4ed;
    border-radius: 12px;
    margin: 15px;

  }

  .report-card___id-block .icon {
    font-size: 20px;
  }

  .icon {
    font-size: 30px;
  }

  .icon-wrapper {
    background-color: #e7ecf9;
    border-radius: 12px;
    width: 40px;
    height: 40px;
    padding: 5px;
    margin: 10px;
  }

  .stat-block {
    display: flex;
    /* height: 40px; */
  }

  .stat-block__info {
    display: flex;
    flex-direction: column;
    line-height: 100%;    
    /* align-items: center; */
    /* text-align: center; */
    justify-content: center;
  }

  .status-block {
    display: flex;
    justify-content: space-between;
    align-items: center;
    line-height: 100%;
    background-color: #d2f4ed;
    padding: 4px 16px;
    border-radius: 12px;

    margin: 15px;
  }
  .status-block .icon {
    font-size: 20px;
  }

  .data-block {
    display: flex;
    /* justify-content: space-between; */
    align-items: center;
    justify-content: start;
    margin: 0px 15px;

  }
  .data-block .icon{
    font-size: 20px;
  }
</style>