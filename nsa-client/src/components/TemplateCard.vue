<template>
  <div class="full-component">
    <el-row>
      <el-col :span="4">
        <span class="template__id-block"><i class="el-icon-message icon" ></i> ID шаблона</span>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="4">
        <span class="template__list-name-block"><i class="el-icon-s-unfold icon" ></i> Название списка </span>
      </el-col>
      <el-col :span="4" :offset="12">
        <el-button plain round class="template__button">Начать рассылку по шаблону <i class="el-icon-edit icon" ></i></el-button>
      </el-col>
      <el-col :span="4">
        <el-button plain round class="template__button">Поделиться шаблоном <i class="el-icon-star-off icon" ></i></el-button>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="4">
        <span><i class="el-icon-document icon" ></i> Текст шаблона:</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <span>TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT </span>
      </el-col>
    </el-row>

  </div>
</template>

<script>
//el-icon-s-unfold
//el-icon-message
//el-icon-document-copy
//el-icon-document
export default {
  name: 'TemplateCard',
  // components: {

  // }
  data() {
      return {
      };
    },
}
</script>

<style scoped>
  .full-component {
    border-radius: 24px;
    background-color: white;
    line-height: 40px;
  }

  .template__id-block {
    padding: 5px 20px;
    background-color: #d2f4ed;
    border-radius: 12px;
  }
  .icon {
    font-size: 20px;
  }



</style>