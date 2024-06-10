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
        <el-button plain round class="template__button" @click="openDialogStartMailingFromTemplate">Начать рассылку по шаблону <i class="el-icon-edit icon" ></i></el-button>
      </el-col>
      <el-col :span="4">
        <el-button plain round class="template__button" @click="dialogFormTemplateSharingVisible=true">Поделиться шаблоном <i class="el-icon-star-off icon" ></i></el-button>
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

    <el-dialog title="Шейринг шаблона" :visible.sync="dialogFormTemplateSharingVisible">
      <el-form :model="formTemplateSharing">
        <el-form-item label="ID получателя">
          <el-input v-model="formTemplateSharing.userName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormTemplateSharingVisible = false">Отменить</el-button>
        <el-button type="primary" @click="dialogFormTemplateSharingVisible = false">Поделиться</el-button>
      </span>
    </el-dialog>

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
  props: {
    templateId: null,
    listName: null,
    text: null
  },
  data() {
      return {
        formTemplateSharing: {
          templateId: null,
          userIdShareTo: null
        },
        dialogFormTemplateSharingVisible: false,
      };
  },
  methods: {
    openDialogStartMailingFromTemplate() {
      this.$confirm('Начать рассылку по шаблону?', 'Старт рассылки', {
        confirmButtonText: 'Да',
        cancelButtonText: 'Нет',
        type: 'warning'
      }).then(() => {
        const data = {
          "templateId": this.templateId
        }
        console.log(data)
        this.$taskResolverService.createTaskFromTemplate(data)
        this.$message({
          type: 'success',
          message: 'Сохранено'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Отменено'
        });
      });
    }
  }
}
</script>

<style scoped>
  .full-component {
    border-radius: 24px;
    background-color: white;
    line-height: 40px;
    margin-bottom: 15px;
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