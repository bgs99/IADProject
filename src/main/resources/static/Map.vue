<template>
    <div class="container">
        <table>
            <col width="80%">
            <col width="20%">
            <tr>
                <td>
                    <img src="pics/map.png" width="100%">
                </td>
                <td>
                    <div>Statistics</div>
                    <div>{{map.name}}</div>
                    Parent: <input type="button" v-if="map.parentId != null"
                                   :value="map.parentName" @click="load(map.parentId)">
                    <div>Danger: {{map.danger}}</div>
                    <input type="button" :value="'Population(' + map.population + ')'">
                    <input type="button" :value="'Agents(' + map.cops + ')'">
                    <input type="button" :value="'Targets(' + map.targets + ')'">
                    <div>Units</div>
                    <input type="button" v-for="unit in map.units" :value="unit.second" @click="load(unit.first)">
                </td>
            </tr>
        </table>
    </div>
</template>

<script>
    export default {
        name: "map",
        props: {
            location: {
                type: Number,
                default: 0
            }
        },
        methods: {
            load: function() {
                axios('/place', {
                    params:{
                        id: this.location
                    },
                    method: 'POST'
                }).then(response => {
                    console.log(response);
                    this.map = response.data;
                }).catch(error => {
                    console.log(error);
                });
            }
        },
        beforeMount(){
            this.load();
        },
        data: {
            map: {}
        }
    }
</script>

<style scoped>

</style>