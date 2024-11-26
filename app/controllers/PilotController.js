import PilotRepository from '../repositories/PilotRepository.js';



class PilotController {
    async index(req, res) {
        const Pilots = await PilotRepository.list();
        return res.render('Pilot/index', { layout: 'main', title: 'Pilotos', Pilots: Pilots });
    }

    async store(req, res) {
        const { name, date, country } = req.body;
        const Pilot = await PilotRepository.create(name, date, country);
        if (Pilot) {
            req.session.success = 'Piloto cadastrado com sucesso!';
            return res.redirect('/Pilot');
        } else {
            req.session.error = 'Erro ao cadastrar Piloto!';
            return res.redirect('/Pilot/create');
        }
    }

    async edit(req, res) {
        const Pilot = await PilotRepository.findById(req.params.id);
        return res.render('Pilot/edit', { layout: 'main', title: 'Editar Piloto', Pilot: Pilot });
    }

    async update(req, res) {
        const { id, name, date, country } = req.body;
        const Pilot = await PilotRepository.update(id, name, date, country);
        if (Pilot) {
            req.session.success = 'Piloto atualizado com sucesso!';
            return res.redirect('/Pilot');
        } else {
            req.session.error = 'Erro ao atualizar Piloto!';
            return res.redirect('/Pilot/edit/' + id);
        }
    }

    async delete(req, res) {
        const Pilot = await PilotRepository.delete(req.body.id);
        if (Pilot) {
            req.session.success = 'Piloto deletado com sucesso!';
            return res.redirect('/Pilot');
        } else {
            req.session.error = 'Erro ao deletar Piloto!';
            return res.redirect('/Pilot');
        }
    }
}


export default new PilotController();