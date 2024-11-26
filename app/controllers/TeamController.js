import TeamRepository from '../repositories/TeamRepository.js';



class TeamController {
    async index(req, res) {
        const Teams = await TeamRepository.list();
        return res.render('Team/index', { layout: 'main', title: 'Equipes', Teams: Teams });
    }

    async store(req, res) {
        const { name, date, country } = req.body;
        const Team = await TeamRepository.create(name, date, country);
        if (Team) {
            req.session.success = 'Equipe cadastrado com sucesso!';
            return res.redirect('/Team');
        } else {
            req.session.error = 'Erro ao cadastrar Equipe!';
            return res.redirect('/Team/create');
        }
    }

    async edit(req, res) {
        const Team = await TeamRepository.findById(req.params.id);
        return res.render('Team/edit', { layout: 'main', title: 'Editar Equipe', Team: Team });
    }

    async update(req, res) {
        const { id, name, date, country } = req.body;
        const Team = await TeamRepository.update(id, name, date, country);
        if (Team) {
            req.session.success = 'Equipe atualizado com sucesso!';
            return res.redirect('/Team');
        } else {
            req.session.error = 'Erro ao atualizar Equipe!';
            return res.redirect('/Team/edit/' + id);
        }
    }

    async delete(req, res) {
        const Team = await TeamRepository.delete(req.body.id);
        if (Team) {
            req.session.success = 'Equipe deletado com sucesso!';
            return res.redirect('/Team');
        } else {
            req.session.error = 'Erro ao deletar Equipe!';
            return res.redirect('/Team');
        }
    }
}


export default new TeamController();