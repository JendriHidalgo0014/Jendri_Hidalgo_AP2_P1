package edu.ucne.jendri_hidalgo_ap2_p1.domain.repository

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.Borrame

interface BorrameRepository {

    suspend fun upsert(borrame: Borrame): Int

}