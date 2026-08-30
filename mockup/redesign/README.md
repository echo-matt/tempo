# Visual redesign draft

A dark-first redesign of Tempo, drafted against the current layouts in
`app/src/main/res`. Nothing here ships — it is a design draft to argue with.

Live canvas: https://claude.ai/code/artifact/83949564-beaf-4db6-b822-d99685ead432

## What is here

| File | Artboard |
| --- | --- |
| `Main.dc.html` | Home, sections 1-8 |
| `HomeContinued.dc.html` | Home, sections 9-16 |
| `Library.dc.html` | Library |
| `AlbumPage.dc.html` | Album page |
| `NowPlaying.dc.html` | Full player |
| `Foundations.dc.html` | Colour, type, shape, and where each change lands in `res/` |
| `Components.dc.html` | The three list patterns and the one-offs |
| `canvas.json` | Artboard positions, pages, notes |

The generated `tempo-redesign.html` (~2.5 MB) is not committed — rebuild it from
these sources with the `design` skill's `seed-canvas.mjs`.

## The direction in one paragraph

Drop the Material 3 baseline purple. Near-black neutrals (`#0C0C0F`) so cover art
carries the colour, Tempo's own logo coral (`#f24b6a`) as the single accent,
`@dimen/radius` from 2dp to 8dp, negative tracking on headings, and a mini-player
that lifts off the bottom edge as a floating card. Navigation, fragments and the
ViewPager2s are unchanged — this is a re-skin plus a re-layout.

## Why this was redrawn

The first version of this canvas drew two home sections. The app renders sixteen,
in three different list shapes, driven by what the Subsonic server returns and what
is enabled in settings. It was a design for a different app, so implementing it
faithfully could never have matched. Every section here is one that exists, under
its real label, in its real order and its real shape.

## Known gaps

- No light-theme screens are drawn; the light palette is specced on Foundations.
- Search is not drawn. It uses Material's `SearchBar` and `SearchView`, which bring
  their own structure. The earlier canvas had a Search screen that owed nothing to
  the real one; it has been removed rather than left to mislead.
- The album and player header gradients are neutral. In the app they would come from
  `Palette` on the loaded cover, falling back to the page background.
