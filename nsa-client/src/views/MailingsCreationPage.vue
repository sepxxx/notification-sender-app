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

    <TemplateCard></TemplateCard>


    <el-dialog title="Создание рассылки" :visible.sync="dialogFormMailingCreationVisible">
      <el-form :model="formMailingCreation">

        <el-form-item label="Текст">
          <el-input type="textarea" v-model="formMailingCreation.text" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="Выбор списка">
          <el-select v-model="formMailingCreation.listName" placeholder="Выбор списка">
            <el-option label="No.1" value="shanghai"></el-option>
            <el-option label="No.2" value="beijing"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormMailingCreationVisible = false">Отменить</el-button>
        <el-button type="primary" @click="dialogFormMailingCreationVisible = false">Начать</el-button>
      </span>
    </el-dialog>


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

import TemplateCard from "@/components/TemplateCard.vue";

export default {
  name: 'MailingsCreationPage',
  components: {
    TemplateCard
  },
  data() {
    return {
      formMailingCreation: {
        listName: '',
        text: '',
      },
      formTemplateSharing: {
        userName: '',
      },
      dialogFormMailingCreationVisible: false,
      dialogFormTemplateSharingVisible: false,
    }
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