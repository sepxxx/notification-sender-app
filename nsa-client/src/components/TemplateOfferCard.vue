<template>
  <div class="full-component">
    <el-row>
      <el-col :span="4">
        <span class="template__id-block"><i class="el-icon-message icon" ></i> ID шаблона: {{templateId}}</span>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="4">
        <span class="template__list-name-block"><i class="el-icon-s-unfold icon" ></i> Список: {{listName}} </span>
      </el-col>
      <el-col :span="4" :offset="12">
        <el-button plain round class="template__button" @click="setTaskTemplateStatus(`ACCEPTED`)">Принять<i class="el-icon-check icon" ></i></el-button>
      </el-col>
      <el-col :span="4">
        <el-button plain round class="template__button " @click="setTaskTemplateStatus(`REJECTED`)">Отклонить <i class="el-icon-close icon" ></i></el-button>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="4">
        <span><i class="el-icon-document icon" ></i> Текст шаблона:</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <span>{{text}}</span>
      </el-col>
    </el-row>

  </div>
</template>

<script>

export default {
  name: 'TemplateOfferCard',
  // components: {

  // }
  props: {
    templateId: null,
    listName: null,
    text: null
  },
  data() {
      return {
      };
  },
  methods: {
    async setTaskTemplateStatus(status) {
        try {
          const data = {templateId: this.templateId, taskTemplateStatus: status};
          console.log(data)
          await this.$taskResolverService.setTaskTemplateStatus(data);
          this.$emit('status-changed');
        } catch (error) {
          this.$message({
            message: error.response.data.message,
            type: 'error'
          });
        } finally {
          this.dialogFormTemplateSharingVisible = false
        }
    }
  }
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