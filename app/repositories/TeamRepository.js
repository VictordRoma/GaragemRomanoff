import { db } from '../config/firebase.js';



class TeamRepository{
    async list(){
        try{
            const Teams = await db.collection('Teams').get();
            return Teams;
        }catch(error){
            console.error('Error getting Teams', error);
        }
    }

    async getById(id){
        try{
            return await db.collection('Teams').doc(id).get();
        }catch(error){
            console.error('Error getting Team by id', error);
        }
    }

    async create(Team){
        try{
            const response = await db.collection('Teams').add(Team);
            return response;
        }catch(error){
            console.error('Error creating Team', error);
        }
    }

    async update(id, Team){
        try{
            await db.collection('Teams').doc(id).update(Team);
            return true;
        }catch(error){
            console.error('Error updating Team', error);
        }
    }

    async delete(id){
        try{
            await db.collection('Teams').doc(id).delete();
            return true;
        }catch(error){
            console.error('Error deleting Team', error);
        }
    }
}


export default new TeamRepository();