<template>
  <div :wide="$route.params.source === 'id'">
    <div class="mission-root">
      <div style="grid-area: mission-target">
        <h1 align="center">
          #{{src.id}} {{src.targetName}}
        </h1>
        <h2>
          {{src.type}}
          <router-link tag="button" :to="`/targets/id/${src.targetId}`">
            Go to target
          </router-link>
        </h2>
        <template id="participation" v-if="$store.getters.field && src.status === 'Создана' && $store.state.user.types.includes(src.type)">
          <template>
            <button v-if="$store.getters.missionId === null"
                    @click="$store.dispatch('participate', src.id); $router.push(`/missions/id/${src.id}`)">Participate</button>
            <button v-else-if="$store.getters.missionId === src.id
                              && !$store.state.currentMission.ready
                              && $store.state.currentMission.weapon !== undefined"
                    @click="$store.dispatch('missionReady')">Ready</button>
          </template>
          <div class="subpanel" v-if="$store.getters.missionId === src.id
                      && $route.params.source === 'id'
                      && !$store.state.currentMission.ready
                      && !src.team.map(q => q.first.id).includes($store.state.user.id)">
            <router-link tag="button" to="/equipment/weapons/page/0"
                         v-if="$store.state.currentMission.weapon === undefined && !$store.state.currentMission.ready">
              Select weapon
            </router-link>
            <div v-else-if="$store.state.currentMission.weapon !== undefined">
              Weapon:
              <br>
              <img :src="`/static/weapons/${$store.state.currentMission.weapon}.jpg`" height="100">
              {{$store.getters.weapons.find(q => q.id === $store.state.currentMission.weapon).name}}
            </div>
            <router-link tag="button" to="/equipment/transport/page/0"
                         v-if="$store.state.currentMission.transport  === undefined && !$store.state.currentMission.ready">
              Select transport (optional)
            </router-link>
            <div v-else-if="$store.state.currentMission.transport !== undefined">
              Transport:
              <br>
              <img :src="`/static/transport/${$store.state.currentMission.transport}.jpg`" height="100">
              {{$store.getters.transport.find(q => q.id === $store.state.currentMission.transport).name}}
            </div>
            <router-link tag="button" to="/registry/people/page/0"
                         v-if="$store.state.currentMission.cover === undefined && !$store.state.currentMission.ready">
              Select cover (optional)
            </router-link>
            <div v-else-if="$store.state.currentMission.cover !== undefined">
              Cover:
              <br>
              <img :src="`/static/people/${$store.state.currentMission.cover}.jpg`" height="100">
              #{{$store.state.currentMission.cover}}
            </div>
          </div>
        </template>
        <button id="start-mission" v-if="currentMission
                      && $store.getters.admin
                      && src.status === 'Создана'
                      && src.team.length >= src.minimalTeam"
                @click="$store.dispatch('startMission'); status = 'Выполняется'">Start</button>
        <button v-else-if="$store.getters.admin && currentMission && src.status === 'Создана'" disabled>{{src.minimalTeam - src.team.length}} more agents to start</button>
        <p>
          Danger level <Danger :val="src.danger"></Danger>
        </p>
        <p>
          Location: <router-link tag="button" :to="`/map/${src.location.first}`">
          {{src.location.second}}
        </router-link>
        </p>
        Current status: {{src.status}} <br>
        <router-link v-if="$route.params.source !== 'id'" tag="button" :to="`/missions/id/${src.id}`">See details</router-link>
        <template id="mission-details-management" v-if="$route.params.source === 'id'">

        <label id="status-management" v-if="currentMission
                      && src.status !== 'Создана' && src.status !== 'Выполнена'">
          Change status:
          <input type="text" v-model="status"/>
          <button v-if="status.length > 0 && status !== src.status" @click="$store.dispatch('setStatus', status)">Change</button>
          <button v-else @click="$store.dispatch('setStatus', 'Выполнена')">Mission complete</button>
        </label>
        <p v-else>
          Status: {{src.status}}
        </p>
        <p>
          {{src.desc}}
        </p>
        <div id="reports" v-if="(src.team.find(q => q.first.id === $store.state.user.id) !== undefined
                                || src.responsible === $store.state.user.id)
                                && !['Создана', 'Выполняется'].includes(src.status)">
          <button v-if="!report.active" @click="report.active = true">Write a report</button>
          <form class="subpanel" v-else @submit.prevent="sendReport">
            <label>Name: <br><input type="text" v-model="report.name" required/></label>
            <br>
            <label>Purpose: <br><input type="text" v-model="report.purp" required/></label>
            <br>
            <label>Subject: <br>
              <select v-model.number="report.agent">
                <option v-for="a in src.team.map(q => q.first)" :key="a.id" :value="a.id">
                  {{a.name}}
                </option>
              </select>
            </label>
            <br>
            <label>Description: <br> <textarea cols="50" rows="10" v-model="report.desc"></textarea></label>
            <br>
            <input v-if="report.agent >=0 && report.desc.length >0 && report.purp.length>0 && report.name.length > 0"
                   type="submit" value="Submit"/>
            <button @click="report.active = false">Hide</button>
          </form>
          <router-link tag="button" :to="`/reports/mission/${src.id}`">Show reports</router-link>
        </div>
        <div id="support" v-if="(src.team.find(q => q.first.id === $store.state.user.id) !== undefined
                                || src.responsible === $store.state.user.id)
                                && !['Создана', 'Выполнена'].includes(src.status)">
          <button v-if="!support.active"
                  @click="support.active = true;
                          $store.dispatch('loadEquipmentTransportByPage', 0);
                          $store.dispatch('loadEquipmentWeaponsByPage', 0);">Request support</button>
          <form class="subpanel" v-else @submit.prevent="requestSupport">
            <label>Amount of soldiers: <br> <input type="number" v-model.number="support.soldiers"/></label>
            <br>
            <label>Weapon: <br>
              <select v-model.number="support.weapons">
                <option v-for="a in $store.getters.weapons" :key="a.id" :value="a.id">
                  {{a.name}}
                </option>
              </select>
            </label>
            <br>
            <label>Transport:
              <br>
              <select v-model.number="support.transport">
                <option v-for="a in $store.getters.transport" :key="a.id" :value="a.id">
                  {{a.name}}
                </option>
              </select>
            </label>
            <br>
            <label>Data:
              <br><textarea cols="50" rows="10" v-model="support.data"></textarea></label>
            <br>
            <input type="submit" value="Submit"/>
            <button @click="support.active = false">Hide</button>
          </form>
          <router-link v-if="$store.getters.admin" tag="button" :to="`/requests/mission/${src.id}`">Go to requests</router-link>
        </div>
        </template>
      </div>

      <template id="mission-details-all" v-if="$route.params.source === 'id'">
      <div style="grid-area: mission-team" v-if="src.team.length > 0">
        <h2 align="center">
          Team
        </h2>
        <table>
          <tr v-for="ag in src.team" :key="ag.first.id">
            <td>
              <router-link :to="`/agents/id/${ag.first.id}`">
                <img :src="`/static/people/${ag.first.name}.jpg`" width="100" height="100"/>
              </router-link>
              <p>
                #{{ag.first.id}} {{ag.first.name}}
              </p>
            </td>
            <td>
              <img :src="`/static/weapons/${ag.second.id}.jpg`" height="100"/>
              <p>
                {{ag.second.name}}
              </p>
            </td>
          </tr>
        </table>
      </div>
      <div style="grid-area: mission-transp" v-if="src.transport.length > 0">
        <h2 align="center">Transport</h2>
        <div v-for="ag in src.transport" :key="ag.id">
          <img :src="`/static/transport/${ag.id}.jpg`" height="100"/>
          {{ag.name}}
        </div>
      </div>
      </template>
    </div>
  </div>
</template>

<script>
  import Danger from './Danger';

  export default {
  name: 'Missions',
    components: {Danger},
    props: ['src'],
    computed: {
      currentMission () {
        return this.$store.getters.missionId === this.src.id
      }
    },
    methods: {
      sendReport () {
        this.$store.dispatch('sendReport', this.report);
        this.report = {
          active: false,
          mission: this.src.id,
          desc: '',
          purp: '',
          agent: undefined,
          name: ''
        };
      },
      requestSupport () {
        this.$store.dispatch('requestSupport', this.support);
        this.support = {
          active: false,
          id: this.src.id,
          data: '',
          soldiers: 0,
          transport: undefined,
          weapons: undefined
        };
      }
    },
    data () {
      return {
        status: this.src.status,
        report: {
          active: false,
          mission: this.src.id,
          desc: '',
          purp: '',
          agent: undefined,
          name: ''
        },
        support: {
          active: false,
          id: this.src.id,
          data: '',
          soldiers: 0,
          transport: undefined,
          weapons: undefined
        }
      }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .subpanel {
    background-color: lightgray;
    color: black;
    border: white medium solid;
    border-radius: 5px;
  }
  @media screen and (min-width: 818px){
    .mission-root {
      display: grid;
      grid-template-columns: 1fr 1fr;
      grid-template-rows: auto auto;
      grid-template-areas:
        "mission-target  mission-target"
        "mission-team    mission-transp"
    }
  }
  @media screen and (max-width: 817px){
    .mission-root {
      display: grid;
      grid-template-columns: 100%;
      grid-template-rows: auto auto auto;
      grid-template-areas:
        "mission-target"
        "mission-transp"
        "mission-team";
    }
  }
</style>
