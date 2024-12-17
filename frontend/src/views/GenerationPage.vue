<script>
import NumberGenerator from '@/components/NumberGenerator.vue';

export default {
  components: {
    NumberGenerator,
  },
};
</script>
<template>
  <div>
    <h2>Générer un numéro unique</h2>
    <div id="generationPage">
      <NumberGenerator/>
    </div>
  </div>
</template>

<style>

#generationPage {
  text-align: left;
  color: #2c3e50;
  margin-top: 60px;
  width: 700px;
}
</style>
