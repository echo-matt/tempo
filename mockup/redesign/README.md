# Visual redesign draft

A dark-first redesign of Tempo, drafted against the current layouts in
`app/src/main/res`. Nothing here ships — it is a design draft to argue with.

Live canvas: https://claude.ai/code/artifact/83949564-beaf-4db6-b822-d99685ead432

## What is here

| File | Artboard |
| --- | --- |
| `Main.dc.html` | Home |
| `Library.dc.html` | Your library |
| `Search.dc.html` | Search |
| `AlbumPage.dc.html` | Album page |
| `NowPlaying.dc.html` | Full player |
| `Foundations.dc.html` | Colour, type, shape, and where each change lands in `res/` |
| `Components.dc.html` | Section header, track row, cards, buttons, mini-player, nav |
| `canvas.json` | Artboard positions, pages, notes |

The generated `tempo-redesign.html` (~2.5 MB) is not committed — rebuild it from
these sources with the `design` skill's `seed-canvas.mjs`.

## The direction in one paragraph

Drop the Material 3 baseline purple. Near-black neutrals (`#0C0C0F`) so cover art
carries the colour, Tempo's own logo coral (`#f24b6a`) as the single accent,
`@dimen/radius` from 2dp to 8dp, negative tracking on headings, and a mini-player
that lifts off the bottom edge as a floating card. Navigation, fragments and the
ViewPager2s are unchanged — this is a re-skin plus a re-layout.

## Known gaps

- No light-theme screens are drawn; the light palette is specced on Foundations.
- The album header gradient is hand-picked. In the app it would come from
  `Palette` on the loaded cover, falling back to the neutral background.
