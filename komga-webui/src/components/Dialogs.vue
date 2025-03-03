<template>
  <div>
    <collection-add-to-dialog
      v-model="addToCollectionDialog"
      :series="addToCollectionSeries"
    />

    <collection-edit-dialog
      v-model="editCollectionDialog"
      :collection="editCollection"
    />

    <confirmation-dialog
      v-model="deleteCollectionDialog"
      :title="collectionsToDeleteSingle ? $t('dialog.delete_collection.dialog_title') : $t('dialog.delete_collection.dialog_title_multiple')"
      :body-html="collectionsToDeleteSingle ? $t('dialog.delete_collection.warning_html', { name: collectionsToDelete.name}) : $t('dialog.delete_collection.warning_multiple_html', { count: collectionsToDelete.length})"
      :confirm-text="collectionsToDeleteSingle ? $t('dialog.delete_collection.confirm_delete', {name: collectionsToDelete.name}) : $t('dialog.delete_collection.confirm_delete_multiple', {count: collectionsToDelete.length})"
      :button-confirm="$t('dialog.delete_collection.button_confirm')"
      button-confirm-color="error"
      @confirm="deleteCollections"
    />

    <read-list-add-to-dialog
      v-model="addToReadListDialog"
      :books="addToReadListBooks"
    />

    <read-list-edit-dialog
      v-model="editReadListDialog"
      :read-list="editReadList"
    />

    <confirmation-dialog
      v-model="deleteReadListDialog"
      :title="readListsToDeleteSingle ? $t('dialog.delete_readlist.dialog_title') : $t('dialog.delete_readlist.dialog_title_multiple')"
      :body-html="readListsToDeleteSingle ? $t('dialog.delete_readlist.warning_html', {name: readListsToDelete.name}) : $t('dialog.delete_readlist.warning_multiple_html', {count: readListsToDelete.length})"
      :confirm-text="readListsToDeleteSingle ? $t('dialog.delete_readlist.confirm_delete', {name: readListsToDelete.name}) : $t('dialog.delete_readlist.confirm_delete_multiple', {count: readListsToDelete.length})"
      :button-confirm="$t('dialog.delete_readlist.button_confirm')"
      button-confirm-color="error"
      @confirm="deleteReadLists"
    />

    <library-edit-dialog
      v-model="editLibraryDialog"
      :library="editLibrary"
    />

    <confirmation-dialog
      v-model="deleteLibraryDialog"
      :title="$t('dialog.delete_library.title')"
      :body-html="$t('dialog.delete_library.warning_html', {name: libraryToDelete.name})"
      :confirm-text="$t('dialog.delete_library.confirm_delete', {name: libraryToDelete.name})"
      :button-confirm="$t('dialog.delete_library.button_confirm')"
      button-confirm-color="error"
      @confirm="deleteLibrary"
    />

    <edit-books-dialog
      v-model="updateBooksDialog"
      :books="updateBooks"
    />

    <bulk-edit-books-dialog
      v-model="updateBulkBooksDialog"
      :books="updateBulkBooks"
    />

    <edit-series-dialog
      v-model="updateSeriesDialog"
      :series="updateSeries"
    />

  </div>
</template>

<script lang="ts">
import CollectionAddToDialog from '@/components/dialogs/CollectionAddToDialog.vue'
import CollectionEditDialog from '@/components/dialogs/CollectionEditDialog.vue'
import EditBooksDialog from '@/components/dialogs/EditBooksDialog.vue'
import EditSeriesDialog from '@/components/dialogs/EditSeriesDialog.vue'
import LibraryEditDialog from '@/components/dialogs/LibraryEditDialog.vue'
import Vue from 'vue'
import ReadListAddToDialog from '@/components/dialogs/ReadListAddToDialog.vue'
import ReadListEditDialog from '@/components/dialogs/ReadListEditDialog.vue'
import {BookDto} from '@/types/komga-books'
import {SeriesDto} from '@/types/komga-series'
import {ERROR} from '@/types/events'
import ConfirmationDialog from '@/components/dialogs/ConfirmationDialog.vue'
import {LibraryDto} from '@/types/komga-libraries'
import BulkEditBooksDialog from '@/components/dialogs/BulkEditBooksDialog.vue'

export default Vue.extend({
  name: 'Dialogs',
  components: {
    BulkEditBooksDialog,
    ConfirmationDialog,
    CollectionAddToDialog,
    CollectionEditDialog,
    ReadListAddToDialog,
    ReadListEditDialog,
    LibraryEditDialog,
    EditBooksDialog,
    EditSeriesDialog,
  },
  computed: {
    // collections
    addToCollectionDialog: {
      get(): boolean {
        return this.$store.state.addToCollectionDialog
      },
      set(val) {
        this.$store.dispatch('dialogAddSeriesToCollectionDisplay', val)
      },
    },
    addToCollectionSeries(): SeriesDto | SeriesDto[] {
      return this.$store.state.addToCollectionSeries
    },
    editCollectionDialog: {
      get(): boolean {
        return this.$store.state.editCollectionDialog
      },
      set(val) {
        this.$store.dispatch('dialogEditCollectionDisplay', val)
      },
    },
    editCollection(): CollectionDto {
      return this.$store.state.editCollection
    },
    deleteCollectionDialog: {
      get(): boolean {
        return this.$store.state.deleteCollectionDialog
      },
      set(val) {
        this.$store.dispatch('dialogDeleteCollectionDisplay', val)
      },
    },
    collectionsToDelete(): CollectionDto | CollectionDto[] {
      return this.$store.state.deleteCollections
    },
    collectionsToDeleteSingle(): boolean {
      return !Array.isArray(this.collectionsToDelete)
    },
    // read lists
    addToReadListDialog: {
      get(): boolean {
        return this.$store.state.addToReadListDialog
      },
      set(val) {
        this.$store.dispatch('dialogAddBooksToReadListDisplay', val)
      },
    },
    addToReadListBooks(): BookDto | BookDto[] {
      return this.$store.state.addToReadListBooks
    },
    editReadListDialog: {
      get(): boolean {
        return this.$store.state.editReadListDialog
      },
      set(val) {
        this.$store.dispatch('dialogEditReadListDisplay', val)
      },
    },
    editReadList(): ReadListDto {
      return this.$store.state.editReadList
    },
    deleteReadListDialog: {
      get(): boolean {
        return this.$store.state.deleteReadListDialog
      },
      set(val) {
        this.$store.dispatch('dialogDeleteReadListDisplay', val)
      },
    },
    readListsToDelete(): ReadListDto | ReadListDto[] {
      return this.$store.state.deleteReadLists
    },
    readListsToDeleteSingle(): boolean {
      return !Array.isArray(this.readListsToDelete)
    },
    // libraries
    editLibraryDialog: {
      get(): boolean {
        return this.$store.state.editLibraryDialog
      },
      set(val) {
        this.$store.dispatch('dialogEditLibraryDisplay', val)
      },
    },
    editLibrary(): LibraryDto | undefined {
      return this.$store.state.editLibrary
    },
    deleteLibraryDialog: {
      get(): boolean {
        return this.$store.state.deleteLibraryDialog
      },
      set(val) {
        this.$store.dispatch('dialogDeleteLibraryDisplay', val)
      },
    },
    libraryToDelete(): LibraryDto {
      return this.$store.state.deleteLibrary
    },
    // books
    updateBooksDialog: {
      get(): boolean {
        return this.$store.state.updateBooksDialog
      },
      set(val) {
        this.$store.dispatch('dialogUpdateBooksDisplay', val)
      },
    },
    updateBooks(): BookDto | BookDto[] {
      return this.$store.state.updateBooks
    },
    // books bulk
    updateBulkBooksDialog: {
      get(): boolean {
        return this.$store.state.updateBulkBooksDialog
      },
      set(val) {
        this.$store.dispatch('dialogUpdateBulkBooksDisplay', val)
      },
    },
    updateBulkBooks(): BookDto[] {
      return this.$store.state.updateBulkBooks
    },
    // series
    updateSeriesDialog: {
      get(): boolean {
        return this.$store.state.updateSeriesDialog
      },
      set(val) {
        this.$store.dispatch('dialogUpdateSeriesDisplay', val)
      },
    },
    updateSeries(): SeriesDto | SeriesDto[] {
      return this.$store.state.updateSeries
    },
  },
  methods: {
    async deleteLibrary() {
      try {
        await this.$store.dispatch('deleteLibrary', this.libraryToDelete)
      } catch (e) {
        this.$eventHub.$emit(ERROR, {message: e.message} as ErrorEvent)
      }
    },
    async deleteReadLists() {
      const toUpdate = (this.readListsToDeleteSingle ? [this.readListsToDelete] : this.readListsToDelete) as ReadListDto[]
      for (const b of toUpdate) {
        try {
          await this.$komgaReadLists.deleteReadList(b.id)
        } catch (e) {
          this.$eventHub.$emit(ERROR, {message: e.message} as ErrorEvent)
        }
      }
    },
    async deleteCollections() {
      const toUpdate = (this.collectionsToDeleteSingle ? [this.collectionsToDelete] : this.collectionsToDelete) as CollectionDto[]
      for (const b of toUpdate) {
        try {
          await this.$komgaCollections.deleteCollection(b.id)
        } catch (e) {
          this.$eventHub.$emit(ERROR, {message: e.message} as ErrorEvent)
        }
      }
    },
  },
})
</script>

<style scoped>

</style>
