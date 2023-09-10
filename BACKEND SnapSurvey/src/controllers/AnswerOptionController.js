const { getAnswerOption } = require('../models/AnswerOptionModel');

  exports.getAnswerOption = async (req, res) => {
  const answerOptionId = req.params.answerOptionId;
  try {
    const answerOption = await getAnswerOption(answerOptionId);

    if (!answerOption) {
      // Ako odgovor nije pronađen, vraćamo 404 status s odgovarajućom porukom
      return res.status(404).json({ message: 'Odgovor nije pronađen.' });
    }

    // Ako je odgovor pronađen, vraćamo ga u odgovoru
    res.status(200).json(answerOption);
  } catch (error) {
    // Ako se dogodi greška, vraćamo 500 status s porukom o grešci
    console.error(error);
    res.status(500).json({ message: 'Greška na serveru.' });
  }
}
