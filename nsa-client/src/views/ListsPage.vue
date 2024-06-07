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
        <el-button plain class="row-zaglavie__button">Создать список <i
            class="el-icon-upload el-icon-right"></i></el-button>
      </el-col>
    </el-row>
    
    <el-table :data="tableData" :default-sort="{ prop: 'date', order: 'descending' }" class="lists-table">
    <el-table-column prop="id" label="ID"></el-table-column>
    <el-table-column prop="date" label="Дата создания" sortable ></el-table-column>
    <el-table-column prop="name" label="Название" ></el-table-column>
    <el-table-column prop="recipientsTotal" label="Получатели"></el-table-column>
    <el-table-column prop="mailingsTotal" label="Рассылки"></el-table-column>
    <el-table-column label="Действие"><el-button plain>Удалить</el-button></el-table-column>
    <el-table-column ><el-button plain>Дополнить</el-button></el-table-column>
    <el-table-column ><el-button plain>Объединить</el-button></el-table-column>

  </el-table>
  </div>
</template>


<script>

export default {
  name: 'ListsPage',
  components: {

  },
  data() {
      return {
        tableData: [{
          id: '777',
          date: '2016-05-03',
          name: 'Tom',
          recipientsTotal: '999',
          mailingsTotal: "3"
        },
        {
          id: '777',
          date: '2016-05-03',
          name: 'Tom',
          recipientsTotal: '999',
          mailingsTotal: "3"
        }, 
        {
          id: '777',
          date: '2016-05-03',
          name: 'Tom',
          recipientsTotal: '999',
          mailingsTotal: "3"
        }, 


      ]
      }
    },
    methods: {

    }
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
</style>