<template>
  <div class="fullpage">
    <el-row class="row-zaglavie">
      <el-col :span="6">
        <div class="zaglavie-block">
          <span class="zaglavie">Создание рассылки</span>
          <span>Выберите шаблон и используйте прошлые рассылки или начните с нуля.</span>
        </div>
      </el-col>
      <el-col :span="4" :offset="14">
        <el-button plain round class="row-zaglavie__button" @click="dialogFormMailingCreationVisible = true">Создать рассылку
          <i class="el-icon-upload el-icon-right"></i></el-button>
      </el-col>
    </el-row>

    <TemplateCard
      v-for="templ in taskTemplates"
      :key="templ.id"
      :list-name="templ.listName"
      :template-id="templ.id"
      :text="templ.text"
    ></TemplateCard>


    <el-dialog title="Создание рассылки" :visible.sync="dialogFormMailingCreationVisible">
      <el-form :model="formMailingCreation">

        <el-form-item label="Текст">
          <el-input type="textarea" v-model="formMailingCreation.text" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="Выбор списка">
          <el-select v-model="formMailingCreation.listName" placeholder="Выбор списка">
            <el-option v-for="listName in listsNames" :key="listName" :label="listName" :value="listName"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormMailingCreationVisible = false">Отменить</el-button>
        <el-button type="primary" @click="saveTask">Начать</el-button>
      </span>
    </el-dialog>
  </div>

  



</template>


<script>

import TemplateCard from "@/components/TemplateCard.vue";

export default {
  name: 'MailingsCreationPage',
  components: {
    TemplateCard
  },
  data() {
    return {
      taskTemplates: [],
      formMailingCreation: {
        listName: '',
        text: '',
      },
      dialogFormMailingCreationVisible: false,
      listsNames: [],
    }
  },
  methods: {
    async getLists() {
      try {
        const response = await this.$recipientsService.getRecipientsLists();
        console.log(response)
        this.listsNames = response.map(item => item.name);
      } catch (error) {
        console.error(error);
      }
    },
    async saveTask() {
      try {
        const data = {"listName": this.formMailingCreation.listName,
        "text": this.formMailingCreation.text};
        const response = await this.$taskResolverService.saveTask(data);
        console.log(response)
      } catch (error) {
        console.error(error);
        this.$message({
          message: error.response.data.message,
          type: 'error'
        });
      } finally {
        this.dialogFormMailingCreationVisible = false;
      }
    },

    async getTaskTemplates() { //TODO: убрать повторный вызов
      try {
        let params = {"taskTemplateStatus": "CREATED"};
        let response = await this.$taskResolverService.getTaskTemplates(params);
        this.taskTemplates = response;
        params = {"taskTemplateStatus": "ACCEPTED"};
        response = await this.$taskResolverService.getTaskTemplates(params);
        this.taskTemplates.concat(response);
        console.log(response)
      } catch (error) {
        console.error(error);
        this.$message({
          message: error.response.data.message,
          type: 'error'
        });
      } finally {
        this.dialogFormMailingCreationVisible = false;
      }
    }
  },
  mounted() {
    this.getLists();
    this.getTaskTemplates();
  }
}
</script>

<style scoped>
  /* .fullpage {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  } */

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
  line-height: 150%;
}

.zaglavie-block span {
  padding: 2px 0px;
}

.row-zaglavie__button {
  background-color: #852876;
  color: white;
}
</style>