<template>
  <div class="home">
    <div class="album py-5 bg-body-tertiary">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <div class="col" v-for="(item, idx) in displayItems" :key="idx">
            <Card :item="item" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Card from '../components/Card.vue'
import axios from 'axios'
import { computed, onMounted, reactive } from 'vue'
import { useStore } from 'vuex';

export default {
  name: "Home",
  components: {
    Card,
  },
  setup() {
    const store = useStore();
    const state = reactive({
      items: []
    })

    const searchText = computed(() => store.state.searchText);
    const searchResults = computed(() => store.state.searchResults);

    onMounted(() => {
      if(!searchText.value) {
        axios.get("/api/items").then(({data}) => {
          console.log(data);
          state.items = data;
        }).catch((error) => {
          console.error("아이템 목록 불러오기 중 오류 발생: ", error);
        })
      }
    })

    const displayItems = computed(() => {
      return searchText.value ? searchResults.value : state.items;
    })


    return {
      state,
      displayItems,
    }
  }
}

</script>
<style scoped>
</style>