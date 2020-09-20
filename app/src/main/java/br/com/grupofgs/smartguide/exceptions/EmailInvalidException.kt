package br.com.grupofgs.smartguide.exceptions

import br.com.grupofgs.smartguide.R
import br.com.grupofgs.smartguide.SmartGuidApplication

class EmailInvalidException :
    Throwable(SmartGuidApplication.context?.resources?.getString(R.string.emailExInvalid))