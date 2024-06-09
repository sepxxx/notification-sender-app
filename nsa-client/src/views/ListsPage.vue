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
        <el-button plain class="row-zaglavie__button" @click="dialogFormListCreationVisible = true">Создать список
          <i class="el-icon-upload el-icon-right"></i></el-button>
      </el-col>
    </el-row>

    <el-table :data="tableData" :default-sort="{ prop: 'date', order: 'descending' }" class="lists-table">
      <el-table-column prop="id" label="ID"></el-table-column>
<!--      <el-table-column prop="date" label="Дата создания" sortable></el-table-column>-->
      <el-table-column prop="date" label="Дата создания"></el-table-column>
      <el-table-column prop="name" label="Название"></el-table-column>
      <el-table-column prop="recipientsTotal" label="Получатели"></el-table-column>
      <el-table-column prop="mailingsTotal" label="Рассылки"></el-table-column>
      <el-table-column label="Действие">
        <template #default="scope">
          <el-button plain @click="deleteList(scope.row.name)">Удалить</el-button>
        </template>
      </el-table-column>
      <el-table-column><el-button plain
          @click="dialogFormListExtensionVisible = true">Дополнить</el-button></el-table-column>
      <el-table-column><el-button plain
          @click="dialogFormListUnionVisible = true">Объединить</el-button></el-table-column>

    </el-table>


    <el-dialog title="Дополнение списка" :visible.sync="dialogFormListExtensionVisible">
      <!-- <el-form :model="form"> -->
      <el-form>
        <el-form-item label="Прикрепите csv файл">
          <el-input type="file" accept=".csv"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormListExtensionVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogFormListExtensionVisible = false">Confirm</el-button>
      </span>
    </el-dialog>

    <el-dialog title="Объединение списков" :visible.sync="dialogFormListUnionVisible" class="form--lists-union">
      <el-form :model="formListUnion">

        <span>Название списка 1</span>
        <el-form-item label="Название объединенного списка">
          <el-input v-model="formListUnion.listNameNew" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="Выбор списка 2">
          <el-select v-model="formListUnion.listName2" placeholder="Выбор списка 2">
            <el-option label="No.1" value="shanghai"></el-option>
            <el-option label="No.2" value="beijing"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormListUnionVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogFormListUnionVisible = false">Confirm</el-button>
      </span>
    </el-dialog>


    <el-dialog title="Создание списка" :visible.sync="dialogFormListCreationVisible" class="form--lists-creation">
      <el-form :model="formListCreation">
        <el-form-item label="Название нового списка">
          <el-input v-model="formListCreation.listName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form>
          <el-form-item label="Прикрепите csv файл">
<!--            <el-input type="file" v-model="formListCreation.csvFile" accept=".csv" ></el-input>-->
<!--            <el-input type="file" id="file" name="file" accept=".csv" ></el-input>-->
            <input type="file" @change="handleFileUpload($event)" accept=".csv">
          </el-form-item>
        </el-form>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormListCreationVisible = false">Отменить</el-button>
        <el-button type="primary" @click="saveList()">Создать</el-button>
      </span>
    </el-dialog>
  </div>
</template>


<script>

export default {
  name: 'ListsPage',
  components: {

  },
  data() {
    return {
      tableData: [],
      dialogFormListExtensionVisible: false,
      dialogFormListUnionVisible: false,
      dialogFormListCreationVisible: false,
      formListUnion: {
        listNameNew: '',
        listName2: '',
      },
      formListCreation: {
        listName: '',
        csvFile: null,
        fileCostyl: null
      }
    };

  },
  mounted() {
    this.getLists();
    // console.log(this.$recipientsService.getSub())
  },
  methods: {
    async getLists() {
      try {
        const response = await this.$recipientsService.getRecipientsLists();
        console.log(response)
        this.tableData = response.map(item => ({
          id: item.id,
          date: "2024-06-09",
          name: item.name,
          recipientsTotal: item.total,
          mailingsTotal: "?"
        }));
      } catch (error) {
        console.error(error);
      }
    },
    async saveList() {
      try {
        const formData = new FormData()
        formData.append("file", this.formListCreation.csvFile);
        formData.append("listName", this.formListCreation.listName);
        // const response = await this.$recipientsService.saveRecipientList(formData);
        await this.$recipientsService.saveRecipientList(formData);
        this.getLists();
        // console.log(response)
      } catch (error) {
        // console.error(error);
        this.$message({
          message: error.response.data.message,
          type: 'error'
        });
      } finally {
        this.dialogFormListCreationVisible = false;
      }
    },
    async deleteList(listName) {
      try {
        console.log(listName);
        await this.$recipientsService.deleteRecipientList(listName);
        this.getLists();
      } catch (error) {
        this.$message({
          message: error.response.data.message,
          type: 'error'
        });
      }
    },
    handleFileUpload( event ){
      console.log("handleFileUpload")
      console.log(event)
      this.formListCreation.csvFile = event.target.files[0];
    },
  },

}
</script>

<style scoped>
.fullpage {
  height: 90%;
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
  line-height: 100%;
}

.zaglavie-block span {
  padding: 2px 0px;
}

.row-zaglavie__button {
  background-color: #852876;
  color: white;
  border-radius: 12px;
}

.lists-table {
  background-color: white;
  border-radius: 24px;
  height: 80%;
  overflow-y: auto;
  line-height: 100%;
}

.form--lists-union {
  line-height: 100%;
  /* display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: left; */

}
</style>